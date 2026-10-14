# DigiShield – Cybercrime Complaint Portal 🛡️💻

DigiShield is a full-stack, secure web-based reporting and monitoring platform designed to streamline cybercrime registration, incident management, and digital evidence handling.

---

## 🚀 Key Features

* **Tailored Safety Categories:** Workflows for Kids Protection, Women Safety, and Senior Citizen fraud prevention (UPI/OTP fraud, identity theft).
* **Automated Evidence Submission:** Secure portal handling incident descriptions, suspect URLs, and attached screenshots/logs.
* **Tracking & Real-time Updates:** Generates unique tracking IDs for each complaint with automated email notifications via JavaMail API.
* **Role-Based Incident Triage:** Dedicated portal modules for citizens (registration/tracking) and administrative triage.

---

## 🛠️ Tech Stack & Architecture

* **Backend:** Java Servlets, JavaMail API, Apache Tomcat 10
* **Frontend:** JSP, HTML5, CSS3, JavaScript
* **Database:** MySQL
* **Deployment Format:** Production Web Archive (`.war`) with integrated source files

---

## ⚙️ Local Setup & Configuration

1. Clone or download the repository.
2. Deploy the `DigiShield.war` directly into your Tomcat `webapps/` folder, or import the source files into Eclipse/IntelliJ.
3. Import the database schema into MySQL:
   ```sql
   CREATE DATABASE digishield;

