# SCRUM DEVELOPMENT - VENDING MACHINE

To run the application:

At the terminal, run the following command:

**gradle clean (optional)**

**gradle build run**


# Type of Roles

## Customer

There are 3 types of customers:<br>
<img width="384" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/200a8740-c623-4dc5-a635-46703d1ca4c0"><img width="384" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/cd7b664d-13d2-4448-b0fa-dc8a7b57da7a"><img width="377" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/45988e38-32c2-4e8b-bac4-82c4f929ace0">


<img width="381" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/c890b1b7-7bc7-4217-9efe-351b53f43dba"><img width="382" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/21ab54bd-210a-4880-9056-d5922c0868de"><img width="383" alt="image"                                       src="https://media.github.sydney.edu.au/user/9189/files/d1b89d9c-4684-4670-9676-f950af531a61"><img width="380" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/72bd7521-da38-4e94-9ea1-85ac76749a22"><img width="379" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/3c23be20-54d1-4c5c-b101-eebc42c36840">

1. Customers who wishes to create an account can click on the link at the bottom of our application which says:
*"click here, to create an account

2. Customers who do not wish to create an account in our application can choose to continue as Guest:
They can do so by clicking on the link which says:
*"<Click here, to continue as guest!>*

3. Customers who already has an account and are able to log in straight after starting the application.


Rule:

- Once the customer has created an account, their username and password will be stored and saved into our database, they then become
an existing user. 

- Only owner users are able to change/modify their role to become a Seller/Owner/Cashier. 

## Seller 

<img width="387" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/0a164c0b-4b4c-4e07-8fa2-4f2edba7809f"><img width="384" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/d2538215-6df9-43c5-8d65-cb19b4dc45a9"><img width="379" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/ff158f4d-f3c6-4b7d-ad51-631c332b49c5"><img width="381" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/018a9e9d-f099-4226-a547-a1d4a48aee43"><img width="390" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/af0b79ba-43af-47e6-8fe1-e8e6b3dcccb3">



## Cashier 
<img width="381" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/eddb98dd-26ab-4d9f-bab8-85850bd9188c"><img width="379" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/ca607ea3-444d-40b3-a4d3-d04940c0121a"><img width="384" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/a496f1de-dab8-4002-9bd8-b98cbe4e4786"><img width="389" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/87de23ea-bf13-4203-bb25-466042f95208">


Cashiers are existing users who are mainly allowed to modify the quantity of each currencies (cash reserve) available in our Vending Machine. 
At any point in time, there is a cancel button on each page on the top right hand corner which will lead the user back to the log in page. 

1. Log in using an existing Cashier account 
2. Click on any of the 3 operations that the Cashier can operate on.

3A. If cashier has clicked on *Report Available Cash*:

    -> It will produce a CSV report on the Available Cash our Vending Machine has.
    
3B. If cashier has clicked on *Summary Transaction*:

    -> It will produce a CSV report on the summary of transactions. 
    
3C. If cashier has clicked on *Modify Cash*:

    1 .The application will prompt the cashier to a new page that displays the quantity of each of the available currencies our
        Vending Machine has.
        
    2. For each of the currencies, there are up and down arrows/buttons which represent increase or decrease quantity respectively. 
    
    3. After clicking on the arrows/buttons on the currencies (if cashier wants to modify), the cashier can click on the button "Modify" at the bottom right. 
    4. The application will then lead the cashier to a page that displays "Operation Complete" that feedbacks to the cashier that the quantity of the currencies 
        has been successfully modified to the corresponding amount. At this stage, the cashier can choose to go back to the page of Operations (point 2) 
        or log out. 
        
    6. If cashier wishes to log out, they can click on the link "Click Me to log out" and the application will prompt them back to the log in page. 
    
    7. If cashier wishes to go back to the page of operations, they can click on the button on the bottom right "BACK TO OPERATION". The application will lead 
        them back to the page at point 2.
        
        
## Owner 
<img width="384" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/375d6ed5-af1f-41b6-a1fe-8926d6d996e6"><img width="378" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/7216de04-2305-454a-8494-d0766e6f54b7"><img width="377" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/a0c32d2c-040a-4774-8192-98ef3cb1097f"><img width="385" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/8370f8af-300f-43fe-997d-31c80fcf9992"><img width="381" alt="image" src="https://media.github.sydney.edu.au/user/9189/files/c405dbfe-9379-4d82-b7bb-46ef5671cfe7">











