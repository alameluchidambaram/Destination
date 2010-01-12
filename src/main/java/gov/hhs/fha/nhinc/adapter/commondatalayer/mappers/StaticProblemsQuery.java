/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.adapter.commondatalayer.mappers;

import gov.hhs.fha.nhinc.adapter.commondatalayer.DODConnectorPortType;
import gov.hhs.fha.nhinc.adapter.commondatalayer.DODConnectorService;
import gov.hhs.fha.nhinc.adapter.commondatalayer.mappers.constants.AdapterCommonDataLayerConstants;
//import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.hl7.v3.CD;
//import org.hl7.v3.COCTMT030000UV04Person;
//import org.hl7.v3.COCTMT050000UV01Patient;
//import org.hl7.v3.COCTMT090000UV01AssignedEntity;
//import org.hl7.v3.COCTMT090000UV01Person;
//import org.hl7.v3.COCTMT090003UVAssignedEntity;
//import org.hl7.v3.CS;
import org.hl7.v3.CareRecordQUPCIN043100UV01RequestType;
import org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType;
//import org.hl7.v3.EDExplicit;
//import org.hl7.v3.ENExplicit;
//import org.hl7.v3.EnExplicitFamily;
//import org.hl7.v3.EnExplicitGiven;
//import org.hl7.v3.EnExplicitPrefix;
//import org.hl7.v3.II;
//import org.hl7.v3.INT;
//import org.hl7.v3.MFMIMT700712UV01Custodian;
//import org.hl7.v3.ParticipationPhysicalPerformer;
import org.hl7.v3.QUPCIN043100UV01QUQIMT020001UV01ControlActProcess;
import org.hl7.v3.QUPCIN043100UV01QUQIMT020001UV01QueryByParameter;
//import org.hl7.v3.QUPCIN043200UV01MFMIMT700712UV01RegistrationEvent;
//import org.hl7.v3.QUPCIN043200UV01MFMIMT700712UV01Subject1;
//import org.hl7.v3.QUPCIN043200UV01MFMIMT700712UV01Subject5;
import org.hl7.v3.QUPCMT040300UV01ParameterList;
//import org.hl7.v3.REPCMT000100UV01Observation;
//import org.hl7.v3.REPCMT004000UV01CareProvisionEvent;
//import org.hl7.v3.REPCMT004000UV01PertinentInformation5;
//import org.hl7.v3.REPCMT004000UV01RecordTarget;
//import org.hl7.v3.SXCMTSExplicit;
//import org.hl7.v3.TELExplicit;
//import org.hl7.v3.XClinicalStatementObservationMood;
//import org.hl7.v3.REPCMT000100UV01Performer3;
import java.io.*;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author kim
 */
public class StaticProblemsQuery {

   private static DODConnectorService service;
   private static Log logger = LogFactory.getLog(StaticProblemsQuery.class);
   private static org.hl7.v3.ObjectFactory factory = new org.hl7.v3.ObjectFactory();

   public static CareRecordQUPCIN043200UV01ResponseType createProblemsResponse(CareRecordQUPCIN043100UV01RequestType request) {
      CareRecordQUPCIN043200UV01ResponseType response = new CareRecordQUPCIN043200UV01ResponseType();

      //check for static/live data flag in properties file
      if (AdapterCommonDataLayerConstants.PROBLEMS_TEST.equalsIgnoreCase("Y")) {

         logger.info("Calling Static Problems Data...");

         // Get Provider OID from the request
         String receiverOID = request.getReceiverOID();

         // Get Patient ID from the request
         QUPCIN043100UV01QUQIMT020001UV01ControlActProcess query = request.getQuery().getControlActProcess();
         QUPCIN043100UV01QUQIMT020001UV01QueryByParameter queryByParam = query.getQueryByParameter().getValue();
         List<QUPCMT040300UV01ParameterList> paramList = queryByParam.getParameterList();
         String reqPatientID = paramList.get(0).getPatientId().getValue().getExtension();

         logger.debug("Retrieving Emulated Data File for : Patient ID: " + reqPatientID + ", receiverOID: " + receiverOID);
         response = _getEmulatedResponse(reqPatientID, receiverOID);

         //20091221 - Removed Static Data and replaced with Data File
         //response.setCareRecord(createSubject(reqPatientID));
      } else {
         //make call to DODConnector
         String COMMON_DATA_LAYER_QNAME = AdapterCommonDataLayerConstants.CDL_QNAME;
         String wsdlUrl = AdapterCommonDataLayerConstants.DOD_CONNECTOR_WSDL;

         try {
            logger.info("Instantiating DOD Connector Service (" + wsdlUrl + ")...");
            service = new DODConnectorService(new URL(wsdlUrl), new QName(COMMON_DATA_LAYER_QNAME, AdapterCommonDataLayerConstants.DOD_CONNECTOR_NAME));
            logger.info("Retrieving the port from the following service: " + service);

            DODConnectorPortType port = service.getCommonDataLayerPort();

            CareRecordQUPCIN043200UV01ResponseType fdmrresponse = port.getProblems(request);

            if (fdmrresponse != null) {
               response = fdmrresponse;
               logger.info("Response =" + fdmrresponse.toString());
            }

         } catch (Exception e) {
            logger.error("Exception in Problems client: " + e);
         }

      }

      return response;
   }

   private static CareRecordQUPCIN043200UV01ResponseType _getEmulatedResponse(String patientID, String receiverOID)
   {
       CareRecordQUPCIN043200UV01ResponseType response = new CareRecordQUPCIN043200UV01ResponseType();
       response = null;

       // Get PROBLEMS_TAG from the properties file
       String problemsTag = AdapterCommonDataLayerConstants.EMULATOR_PROBLEMS_TAG;

       // Get MEDS_RESPONSE_TYPE from the properties file
       String problemsResponseType = AdapterCommonDataLayerConstants.EMULATOR_PROBLEMS_RESPONSE_TYPE;

       // Get the EMULATOR_DATA_LOCATION from the properties file
       String dataPath = AdapterCommonDataLayerConstants.EMULATOR_DATA_LOCATION;

       // Build URL to file
       String responseFile = null;
       String responseNotFoundFile = null;
       String dataFile = null;
       boolean fileExists = true;

       responseFile = dataPath + receiverOID + "_" + patientID + "_" + problemsTag + "_" + problemsResponseType + ".xml";
       dataFile = responseFile;

       File testFile = new File(responseFile);
       if (!testFile.exists())
       {
           String notFoundTag = AdapterCommonDataLayerConstants.EMULATOR_NO_PATIENT_ID_LABEL;
           responseNotFoundFile = dataPath + receiverOID + "_" + notFoundTag + "_" + problemsTag + "_" + problemsResponseType + ".xml";
           dataFile = responseNotFoundFile;

           File notFoundFile = new File (responseNotFoundFile);
           if(!notFoundFile.exists())
           {
               fileExists = false;
               logger.error("Emulator Data Files Not Found : 1)" + responseFile + "   2)" + responseNotFoundFile);
           }
       }

       if(fileExists)
       {
          logger.debug("Emulated Data File Found: " + dataFile + "  ... Creating Response");
          try{
              JAXBContext jContext = JAXBContext.newInstance(CareRecordQUPCIN043200UV01ResponseType.class);
              Unmarshaller unmarshaller = jContext.createUnmarshaller();
              JAXBElement<CareRecordQUPCIN043200UV01ResponseType> element = unmarshaller.unmarshal(new StreamSource(dataFile), CareRecordQUPCIN043200UV01ResponseType.class);
              response = element.getValue();
              logger.debug("Response Creation Successful");
             }
             catch (Exception ex)
             {
                logger.error("Error Extracting data from " + dataFile + "  --  " + ex);
             }
       }

       return response;
   } // _getEmulatedResponse

}
