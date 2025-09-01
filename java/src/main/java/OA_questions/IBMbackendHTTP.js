
function pulseRate(diagnosisName, doctorId) {
    
    const baseURL = 'https://jsonmock.hackerrank.com/api/medical_records?page='
    let pageData = getPageData(1)
    const total_pages = pageData.total_pages

    let totalPulses = 0
    let totalPatients = 0
    
    for (let i = 1; i <= total_pages; ++i) {
        pageData = getPageData(i);
        const patientRecords = pageData.data
        for (let j = 0; j < patientRecords.length; ++j) {
            const curRecord = patientRecords[j];
            if (curRecord.diagnoses.name == diagnosesName && curRecord.doctor.id == doctorId) {
                totalPulses += curRecord.vitals.pulse
                ++totalPatients
            }
        }
    }
    return totalPatients === 0 ? 0 : Math.floor(totalPulses/totalPatients)

    function getPageData(page) {
        const request = new XMLHttpRequest()
        request.open('GET', `${baseURL}${page}`, false)
        request.send();
        return JSON.parse(request.responseText)
    }

    // async function getPageData(page) {
    //     const response = await fetch(`${baseURL}${page}`)
    //     return await response.json()
    // }
}
