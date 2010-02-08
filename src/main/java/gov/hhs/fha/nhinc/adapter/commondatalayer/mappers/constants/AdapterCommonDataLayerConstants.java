/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.adapter.commondatalayer.mappers.constants;

import java.util.Properties;
import gov.hhs.fha.nhinc.properties.PropertyAccessor;

/**
 *
 * @author A22387
 */
public class AdapterCommonDataLayerConstants
{
    public static final String ADAPTER_PROPERTIES_FILENAME = "adapter_common_datalayer";
    public static final String DOD_CONNECTOR_NAME;
    public static final String DOD_CONNECTOR_WSDL;

    public static final String CDL_QNAME;

    //static data switches
    public static final String ALLERGIES_TEST;
    public static final String PROBLEMS_TEST;
    public static final String PATIENT_INFO_TEST;
    public static final String MEDICATIONS_TEST;

        // Added by FHA for successful build
    public static final String EMULATOR_ALLERGIES_TAG = "ALLERGIES";
    public static final String EMULATOR_MEDS_TAG = "MEDS";
    public static final String EMULATOR_PATIENT_INFO_TAG = "PATIENT_INFO";
    public static final String EMULATOR_PROBLEMS_TAG = "PROBLEMS";
    public static final String EMULATOR_ALLERGIES_RESPONSE_TYPE = "CareRecordQUPCIN043200UV01Response";
    public static final String EMULATOR_MEDS_RESPONSE_TYPE = "CareRecordQUPCIN043200UV01Response";
    public static final String EMULATOR_PATIENT_INFO_RESPONSE_TYPE = "PatientDemographicsPRPAMT201303UV02Response";
    public static final String EMULATOR_PROBLEMS_RESPONSE_TYPE = "CareRecordQUPCIN043200UV01Response";
    public static final String EMULATOR_DATA_LOCATION;
    public static final String EMULATOR_FIND_PATIENTS_TAG = "FIND_PATIENTS";
    public static final String EMULATOR_FIND_PATIENTS_RESPONSE_TYPE ="FindPatientsPRPAMT201310UVResponse";
    public static final String EMULATOR_NO_LAST_NAME_LABEL = "UnknownLastName";
    public static final String EMULATOR_NO_FIRST_NAME_LABEL = "UnknownFirstName";
    public static final String EMULATOR_NO_GENDER_LABEL = "UnknowGender";
    public static final String EMULATOR_NO_DOB_LABEL = "UnknowDOB";
    public static final String EMULATOR_NO_PATIENT_ID_LABEL = "UnknownPatientID";

    static
    {
        String sDOD_CONNECTOR_NAME = null;
        String sDOD_CONNECTOR_WSDL = null;

        String sCDL_QNAME = null;

        String sEMULATOR_DATA_LOCATION = null;
        
        //static data switches
        String sALLERGIES_TEST = null;
        String sPROBLEMS_TEST = null;
        String sPATIENT_INFO_TEST = null;
        String sMEDICATIONS_TEST = null;

        try
        {
            sDOD_CONNECTOR_NAME = PropertyAccessor.getProperty(ADAPTER_PROPERTIES_FILENAME, "dod_connector.name");
            sDOD_CONNECTOR_WSDL = PropertyAccessor.getProperty(ADAPTER_PROPERTIES_FILENAME,"dod_connector.wsdl");
            sCDL_QNAME = PropertyAccessor.getProperty(ADAPTER_PROPERTIES_FILENAME,"common_datalayer.qname");

            //static data switches
            sALLERGIES_TEST = PropertyAccessor.getProperty(ADAPTER_PROPERTIES_FILENAME,"allergies_test");
            sPROBLEMS_TEST = PropertyAccessor.getProperty(ADAPTER_PROPERTIES_FILENAME,"problems_test");
            sMEDICATIONS_TEST = PropertyAccessor.getProperty(ADAPTER_PROPERTIES_FILENAME,"medications_test");
            sPATIENT_INFO_TEST = PropertyAccessor.getProperty(ADAPTER_PROPERTIES_FILENAME,"patient_info_test");

            sEMULATOR_DATA_LOCATION = PropertyAccessor.getProperty(ADAPTER_PROPERTIES_FILENAME, "emulator_test_directory");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        DOD_CONNECTOR_NAME = sDOD_CONNECTOR_NAME;
        DOD_CONNECTOR_WSDL = sDOD_CONNECTOR_WSDL;
        CDL_QNAME = sCDL_QNAME;
        EMULATOR_DATA_LOCATION = sEMULATOR_DATA_LOCATION;

        //static data tests
        ALLERGIES_TEST = sALLERGIES_TEST;
        PROBLEMS_TEST = sPROBLEMS_TEST;
        MEDICATIONS_TEST = sMEDICATIONS_TEST;
        PATIENT_INFO_TEST = sPATIENT_INFO_TEST;
    }
}