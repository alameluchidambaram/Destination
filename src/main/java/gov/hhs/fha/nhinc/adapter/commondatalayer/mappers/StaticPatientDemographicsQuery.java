/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.adapter.commondatalayer.mappers;

import gov.hhs.fha.nhinc.adapter.commondatalayer.DODConnectorPortType;
import gov.hhs.fha.nhinc.adapter.commondatalayer.DODConnectorService;
import gov.hhs.fha.nhinc.adapter.commondatalayer.mappers.constants.AdapterCommonDataLayerConstants;
import java.net.URL;
//import java.util.List;
import javax.xml.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.hl7.v3.BL;
//import org.hl7.v3.CE;
//import org.hl7.v3.COCTMT030207UVPerson;
//import org.hl7.v3.COCTMT150003UV03Organization;
//import org.hl7.v3.EnExplicitFamily;
//import org.hl7.v3.EnExplicitGiven;
//import org.hl7.v3.II;
//import org.hl7.v3.IVLTSExplicit;
//import org.hl7.v3.ONExplicit;
//import org.hl7.v3.PNExplicit;
import org.hl7.v3.PRPAIN201307UV02QUQIMT021001UV01ControlActProcess;
//import org.hl7.v3.PRPAMT201303UVContactParty;
//import org.hl7.v3.PRPAMT201303UVLanguageCommunication;
//import org.hl7.v3.PRPAMT201303UVPatient;
//import org.hl7.v3.PRPAMT201303UVPerson;
import org.hl7.v3.PRPAMT201307UVParameterList;
import org.hl7.v3.PRPAMT201307UVQueryByParameter;
import org.hl7.v3.PatientDemographicsPRPAIN201307UV02RequestType;
import org.hl7.v3.PatientDemographicsPRPAMT201303UV02ResponseType;
//import org.hl7.v3.TSExplicit;
import java.io.*;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author kim
 */
public class StaticPatientDemographicsQuery {

   private static DODConnectorService service;
   private static Log logger = LogFactory.getLog(StaticPatientDemographicsQuery.class);
   private static org.hl7.v3.ObjectFactory factory = new org.hl7.v3.ObjectFactory();

   public static PatientDemographicsPRPAMT201303UV02ResponseType createPatientDemographicsResponse(PatientDemographicsPRPAIN201307UV02RequestType request) {
      PatientDemographicsPRPAMT201303UV02ResponseType response = new PatientDemographicsPRPAMT201303UV02ResponseType();

      //check for static vs. live data test
      if (AdapterCommonDataLayerConstants.PATIENT_INFO_TEST.equalsIgnoreCase("Y")) {
         logger.info("Calling Static Patient Info Data...");

         String rcvHomeCommunity = request.getReceiverOID();
         PRPAIN201307UV02QUQIMT021001UV01ControlActProcess query = request.getQuery().getControlActProcess();

         PRPAMT201307UVQueryByParameter queryByParam = query.getQueryByParameter().getValue();
         PRPAMT201307UVParameterList paramList = queryByParam.getParameterList();
         String reqPatientID = paramList.getPatientIdentifier().get(0).getValue().get(0).getExtension();

         logger.debug("Retrieving Emulated Data File for : Patient ID: " + reqPatientID + ", receiverOID: " + rcvHomeCommunity);
         response = _getEmulatedResponse(reqPatientID, rcvHomeCommunity);

         // 20091221 - Removed Static Data and replaced with Data Files
         //II subjectId = paramList.getPatientIdentifier().get(0).getValue().get(0);
         //response.setSubject(createSubject(subjectId));
      } else {
         //make call to DODConnector
         String COMMON_DATA_LAYER_QNAME = AdapterCommonDataLayerConstants.CDL_QNAME;
         String wsdlUrl = AdapterCommonDataLayerConstants.DOD_CONNECTOR_WSDL;

         try {
            logger.info("Instantiating DOD Connector Service (" + wsdlUrl + ")...");
            service = new DODConnectorService(new URL(wsdlUrl), new QName(COMMON_DATA_LAYER_QNAME, AdapterCommonDataLayerConstants.DOD_CONNECTOR_NAME));
            logger.info("Retrieving the port from the following service: " + service);

            DODConnectorPortType port = service.getCommonDataLayerPort();

            PatientDemographicsPRPAMT201303UV02ResponseType fdmrresponse = port.getPatienInfo(request);

            if (fdmrresponse != null) {
               response = fdmrresponse;
               logger.info("Response =" + fdmrresponse.toString());
            }

         } catch (Exception e) {
            logger.info("Exception in PatientInfo client: " + e);
         }
      }

      return response;
   }

   private static PatientDemographicsPRPAMT201303UV02ResponseType _getEmulatedResponse(String patientID, String receiverOID)
   {
       PatientDemographicsPRPAMT201303UV02ResponseType response = new PatientDemographicsPRPAMT201303UV02ResponseType();
       response = null;

       // Get PATIENT_INFO_TAG from the properties file
       String patientInfoTag = AdapterCommonDataLayerConstants.EMULATOR_PATIENT_INFO_TAG;

       // Get PATIENT_INFO_RESPONSE_TYPE from the properties file
       String patientInfoResponseType = AdapterCommonDataLayerConstants.EMULATOR_PATIENT_INFO_RESPONSE_TYPE;

       // Get the EMULATOR_DATA_LOCATION from the properties file
       String dataPath = AdapterCommonDataLayerConstants.EMULATOR_DATA_LOCATION;

       // Build URL to file
       String responseFile = null;
       String responseNotFoundFile = null;
       String dataFile = null;
       boolean fileExists = true;

       responseFile = dataPath + receiverOID + "_" + patientID + "_" + patientInfoTag + "_" + patientInfoResponseType + ".xml";
       dataFile = responseFile;

       File testFile = new File(responseFile);
       if (!testFile.exists())
       {
           String notFoundTag = AdapterCommonDataLayerConstants.EMULATOR_NO_PATIENT_ID_LABEL;
           responseNotFoundFile = dataPath + receiverOID + "_" + notFoundTag + "_" + patientInfoTag + "_" + patientInfoResponseType + ".xml";
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
              JAXBContext jContext = JAXBContext.newInstance(PatientDemographicsPRPAMT201303UV02ResponseType.class);
              Unmarshaller unmarshaller = jContext.createUnmarshaller();
              JAXBElement<PatientDemographicsPRPAMT201303UV02ResponseType> element = unmarshaller.unmarshal(new StreamSource(dataFile), PatientDemographicsPRPAMT201303UV02ResponseType.class);
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
