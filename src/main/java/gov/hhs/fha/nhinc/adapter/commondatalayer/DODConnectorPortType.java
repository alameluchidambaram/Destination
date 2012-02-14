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
package gov.hhs.fha.nhinc.adapter.commondatalayer;

import javax.jws.WebMethod;

import javax.jws.WebParam;

import javax.jws.WebResult;

import javax.jws.WebService;

import javax.jws.soap.SOAPBinding;

import javax.xml.bind.annotation.XmlSeeAlso;

import org.hl7.v3.CareRecordQUPCIN043100UV01RequestType;

import org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType;

import org.hl7.v3.FindEncountersPRPAIN900300UV02RequestType;

import org.hl7.v3.FindEncountersPRPAMT900350UV02ResponseType;

import org.hl7.v3.FindPatientsPRPAIN201305UV02RequestType;

import org.hl7.v3.FindPatientsPRPAMT201310UV02ResponseType;

import org.hl7.v3.ObjectFactory;

import org.hl7.v3.PatientDemographicsPRPAIN201307UV02RequestType;

import org.hl7.v3.PatientDemographicsPRPAMT201303UV02ResponseType;

/**
 * 
 * This class was generated by the JAX-WS RI.
 * 
 * JAX-WS RI 2.1.3.1-hudson-749-SNAPSHOT
 * 
 * Generated source version: 2.1
 * 
 * 
 */

@WebService(name = "DODConnectorPortType", targetNamespace = "urn:gov:hhs:fha:nhinc:adapter:commondatalayer")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({

ObjectFactory.class

})
public interface DODConnectorPortType {

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.FindEncountersPRPAMT900350UV02ResponseType
     */

    @WebMethod(operationName = "GetEncounters", action = "urn:GetEncounters")
    @WebResult(name = "FindEncounters_PRPA_MT900350UV02Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public FindEncountersPRPAMT900350UV02ResponseType getEncounters(

            @WebParam(name = "FindEncounters_PRPA_IN900300UV02Request", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            FindEncountersPRPAIN900300UV02RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetProcedures", action = "urn:Procedures")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getProcedures(

            @WebParam(name = "CareRecord_QUPC_IN043100UV01ProceduresRequest", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetMedications", action = "urn:Medications")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getMedications(

            @WebParam(name = "CareRecord_QUPC_IN043100UV01MedicationsRequest", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetAllergies", action = "urn:Allergies")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getAllergies(

            @WebParam(name = "CareRecord_QUPC_IN043100UV01AllergiesRequest", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetVitals", action = "urn:Vitals")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getVitals(

            @WebParam(name = "CareRecord_QUPC_IN043100UV01VitalsRequest", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetProblems", action = "urn:Problems")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getProblems(

            @WebParam(name = "CareRecord_QUPC_IN043100UV01ProblemsRequest", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetTestResults", action = "urn:TestResults")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getTestResults(

            @WebParam(name = "CareRecord_QUPC_IN043100UV01TestResultsRequest", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetAdmissions", action = "urn:Admissions")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getAdmissions(

            @WebParam(name = "CareRecord_QUPC_IN043100UV01AppointmentsRequest", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetAppointments", action = "urn:Appointments")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getAppointments(

            @WebParam(name = "CareRecord_QUPC_IN043100UV01AppointmentsRequest", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetImmunizations", action = "urn:Immunizations")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getImmunizations(

            @WebParam(name = "CareRecord_QUPC_IN043100UV01ImmunizationsRequest", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetFamilyHistory", action = "urn:FamilyHistory")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getFamilyHistory(

            @WebParam(name = "CareRecord_QUPC_IN043100UV01FamilyHistoryRequest", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetSocialHistory", action = "urn:SocialHistory")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getSocialHistory(

            @WebParam(name = "CareRecord_QUPC_IN043100UV01SocialHistoryRequest", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetInsurances", action = "urn:Insurances")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getInsurances(

    @WebParam(name = "CareRecord_QUPC_IN043100UV01Request", targetNamespace = "urn:hl7-org:v3", partName = "param0")
    CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.CareRecordQUPCIN043200UV01ResponseType
     */

    @WebMethod(operationName = "GetOrders", action = "urn:Orders")
    @WebResult(name = "CareRecord_QUPC_IN043200UV01Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public CareRecordQUPCIN043200UV01ResponseType getOrders(

            @WebParam(name = "CareRecord_QUPC_IN043100UV01OrdersRequest", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            CareRecordQUPCIN043100UV01RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.PatientDemographicsPRPAMT201303UV02ResponseType
     */

    @WebMethod(operationName = "GetPatienInfo", action = "urn:GetPatienInfo")
    @WebResult(name = "PatientDemographics_PRPA_MT201303UV02Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public PatientDemographicsPRPAMT201303UV02ResponseType getPatienInfo(

            @WebParam(name = "PatientDemographics_PRPA_IN201307UV02Request", targetNamespace = "urn:hl7-org:v3", partName = "param0")
            PatientDemographicsPRPAIN201307UV02RequestType param0);

    /**
     * 
     * 
     * 
     * @param param0
     * 
     * @return
     * 
     *         returns org.hl7.v3.FindPatientsPRPAMT201310UVResponseType
     */

    @WebMethod(operationName = "FindPatients", action = "urn:FindPatients")
    @WebResult(name = "FindPatients_PRPA_MT201310UV02Response", targetNamespace = "urn:hl7-org:v3", partName = "output")
    public FindPatientsPRPAMT201310UV02ResponseType findPatients(

    @WebParam(name = "FindPatients_PRPA_IN201305UV02Request", targetNamespace = "urn:hl7-org:v3", partName = "param0")
    FindPatientsPRPAIN201305UV02RequestType param0);

}
