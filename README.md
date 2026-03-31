# Bank Application - Enterprise Java

A secure and scalable banking management application built using the **Jakarta EE (Java EE)** platform. This multi-module project demonstrates enterprise integration patterns and secure financial transaction handling.

## 🚀 Key Technologies
* **Backend Logic:** Jakarta Enterprise Beans (EJB) 3.x
* **Architecture:** Multi-module Maven Project (EAR, EJB, Web, Core)
* **Persistence:** Jakarta Persistence API (JPA) / Hibernate
* **Application Server:** GlassFish / Payara
* **Build Tool:** Maven 3

## ✨ Features Breakdown

* **AccountManagementBean:** Handles core account operations.
* **Admin Management:** Dedicated EJB beans (`AdminUserManagementBean`, `AdminAccountManagementBean`, `AdminTransactionBean`) for administrative control and security.
* **Fund Transfer:** Secure internal and scheduled fund transfers (`FundTransferBean`, `ScheduledTransferBean`).
* **Automated Services:** Scheduled interest calculation service (`InterestCalculationBean`).
