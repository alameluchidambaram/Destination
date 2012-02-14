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
import java.net.URL;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.CareRecordQUPCIN043100UV01RequestType;
import org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType;
import org.hl7.v3.QUPCIN043100UV01QUQIMT020001UV01ControlActProcess;
import org.hl7.v3.QUPCIN043100UV01QUQIMT020001UV01QueryByParameter;
import org.hl7.v3.QUPCMT040300UV01ParameterList;
import java.io.*;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;

/**
 * 
 * @author kim
 */
public class StaticProblemsQuery {

    private static Log logger = LogFactory.getLog(StaticProblemsQuery.class);
    private static org.hl7.v3.ObjectFactory factory = new org.hl7.v3.ObjectFactory();

    public static CareRecordQUPCIN043200UV01ResponseType createProblemsResponse(
            CareRecordQUPCIN043100UV01RequestType request) {
        CareRecordQUPCIN043200UV01ResponseType response = new CareRecordQUPCIN043200UV01ResponseType();

        // check for static/live data flag in properties file
        if (AdapterCommonDataLayerConstants.PROBLEMS_TEST.equalsIgnoreCase("Y")) {

            logger.info("Calling Static Problems Data...");

            // Get Provider OID from the request
            String receiverOID = request.getReceiverOID();

            // Get Patient ID from the request
            QUPCIN043100UV01QUQIMT020001UV01ControlActProcess query = request.getQuery().getControlActProcess();
            QUPCIN043100UV01QUQIMT020001UV01QueryByParameter queryByParam = query.getQueryByParameter().getValue();
            List<QUPCMT040300UV01ParameterList> paramList = queryByParam.getParameterList();
            String reqPatientID = paramList.get(0).getPatientId().getValue().getExtension();

            logger.debug("Retrieving Emulated Data File for : Patient ID: " + reqPatientID + ", receiverOID: "
                    + receiverOID);
            response = _getEmulatedResponse(reqPatientID, receiverOID);

            // 20091221 - Removed Static Data and replaced with Data File
            // response.setCareRecord(createSubject(reqPatientID));
        } else {
            logger.debug("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
            logger.debug("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
            logger.debug(" Insert Adapter Agency specific dynamic document data accessors here ");
            logger.debug("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
            logger.debug("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");

        }

        return response;
    }

    private static CareRecordQUPCIN043200UV01ResponseType _getEmulatedResponse(String patientID, String receiverOID) {
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

        responseFile = dataPath + receiverOID + "_" + patientID + "_" + problemsTag + "_" + problemsResponseType
                + ".xml";
        dataFile = responseFile;

        File testFile = new File(responseFile);
        if (!testFile.exists()) {
            String notFoundTag = AdapterCommonDataLayerConstants.EMULATOR_NO_PATIENT_ID_LABEL;
            responseNotFoundFile = dataPath + receiverOID + "_" + notFoundTag + "_" + problemsTag + "_"
                    + problemsResponseType + ".xml";
            dataFile = responseNotFoundFile;

            File notFoundFile = new File(responseNotFoundFile);
            if (!notFoundFile.exists()) {
                fileExists = false;
                logger.error("Emulator Data Files Not Found : 1)" + responseFile + "   2)" + responseNotFoundFile);
            }
        }

        if (fileExists) {
            logger.debug("Emulated Data File Found: " + dataFile + "  ... Creating Response");
            try {
                JAXBContext jContext = JAXBContext.newInstance(CareRecordQUPCIN043200UV01ResponseType.class);
                Unmarshaller unmarshaller = jContext.createUnmarshaller();
                JAXBElement<CareRecordQUPCIN043200UV01ResponseType> element = unmarshaller.unmarshal(new StreamSource(
                        dataFile), CareRecordQUPCIN043200UV01ResponseType.class);
                response = element.getValue();
                logger.debug("Response Creation Successful");
            } catch (Exception ex) {
                logger.error("Error Extracting data from " + dataFile + "  --  " + ex);
            }
        }

        return response;
    } // _getEmulatedResponse

}
