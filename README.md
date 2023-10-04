# Adam Zhou - Project Phase 0 - Hospital Organizer

## Introduction:
My mother is a registered nurse and everytime she comes back from work, she usually complains about how disorganized
and chaotic the hospital is. Therefore, I wanted to create a tool that could help a hospital keep track of patients
and available doctors and nurses within a hospital. The application created will help keep track of past medical
histories of the patient, and also help manage doctors by keeping track of which patient is assigned which 
doctor. Some people who might want to use the application 
could be:

- Doctors (to keep track of patient medical histories)
- Hospital Administrators (to manage doctors and check capacity)
- Nurses and Pharmacists (to keep track of patient's past allergies)

The project will consist of the following two classes:

- Doctor
  - Specialty 
  - List of Patients

- Patient
  - Medical History
  - Allergies
## User Stories:

- As a user, I want to be able to add a patients current illness onto their medical history
- As a user, I want to be able to add a patient to a doctor
- As a user, I want to be able to see all patients who are being taken care of by the same doctor
- As a user, I want to be able to change a patient's information
- As a user, I want to be able to save all the current doctor and patient information in a hospital
- As a user, I want to be able to load a past hospital file with all the patients and doctors.

# Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by 
pressing add patient to create a new patient, and then admitting the patient to a doctor under the patients tab (
press home, add patient, enter the information, press add patient, then go to the patients tab, click the patient,
click the doctor, press admit)
- You can remove Xs from a Y by going to the patients tab, clicking on a admitted patient, 
and the pressing the release buttons
- You can locate my visual component by clicking on any doctor on the Doctor tab to see their picture
- You can save the state of my application by going to the home tab and pressing the 
- You can reload the state of my application by going to the home tab and pressing the save hospital state button

## Bibliography:
- JsonWriter and JsonReader were written with reference to JsonSeralizationDemo
(https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)
- Ui and Tabs were written with reference to 
SmartHome(https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters.git)
- Doctor Icon (<a href="https://www.flaticon.com/free-icons/doctor" title="doctor icons">Doctor icons created by Freepik - Flaticon</a>)