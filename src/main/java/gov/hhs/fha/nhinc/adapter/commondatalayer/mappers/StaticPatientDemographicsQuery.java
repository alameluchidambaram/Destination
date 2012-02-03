/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services. 
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 *     * Redistributions of source code must retain the above 
 *       copyright notice, this list of conditions and the following disclaimer. 
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the documentation 
 *       and/or other materials provided with the distribution. 
 *     * Neither the name of the United States Government nor the 
 *       names of its contributors may be used to endorse or promote products 
 *       derived from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package gov.hhs.fha.nhinc.adapter.commondatalayer.mappers;

import gov.hhs.fha.nhinc.adapter.commondatalayer.mappers.constants.AdapterCommonDataLayerConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.PRPAIN201307UV02QUQIMT021001UV01ControlActProcess;
import org.hl7.v3.PRPAMT201307UVParameterList;
import org.hl7.v3.PRPAMT201307UVQueryByParameter;
import org.hl7.v3.PatientDemographicsPRPAIN201307UV02RequestType;
import org.hl7.v3.PatientDemographicsPRPAMT201303UV02ResponseType;
import java.io.*;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author kim
 */
public class StaticPatientDemographicsQuery {

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
          logger.debug("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
          logger.debug("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
          logger.debug(" Insert Adapter Agency specific dynamic document data accessors here ");
          logger.debug("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
          logger.debug("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
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
