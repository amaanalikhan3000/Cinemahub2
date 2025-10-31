# 🎬 CinemaHub Postman Test Collection

This Postman collection is designed to test all API endpoints of the CinemaHub backend — including user authentication, movie listing, booking, OTP verification, and ticket retrieval.

## ⚙️ Setup Instructions
- 1️⃣ Import the Collection

   -  Download the .json file for the CinemaHub Postman Collection.
    
   -  In Postman, click File → Import → Upload Files, and select the file.

- 2️⃣ Set Environment Variables

- Before running the APIs, open your Postman Environment and define the following variables:
```
Variable	           Description	                         Example
baseURL	           Base URL for your backend	       http://localhost:8080
userName               your user name                      your user name
email        	 User email for login/register	             your email
password	         User password	                   your password
movieName          movie name that exists in DB               eg:dunki
authToken	        JWT token returned after login  (auto-set in script)
OTP                   OTP sent to your email            (auto-set in script)
showId	          Show ID for booking seats    show Id of the movie you want to watch
```
💡 You can update these values in Postman → Environment → Create Environment → Name it CINEMAHUB → define the above mentioned variables and select that environment when running APIs.

- 3️⃣ Run the Collection

    - Click Run Collection in Postman (top-right corner).
    
    - Make sure all environment variables are defined.

APIs will execute in order — registration → login → movies → bookings → OTP → ticket → logout.

## 🔐 Authentication Notes

Some APIs require a Bearer Token.

After successful login, the token will automatically be saved in the variable {{authToken}}.

Postman scripts handle token injection automatically for all protected routes.

⚠️ Important:

- The token may expire after a certain time.
If you get a 403 Forbidden or 401 Unauthorized, simply run the Login API again to refresh your token.

- Logout only after testing all token-based APIs, otherwise your session will end early.
 Do not run Logout until you’ve tested all authenticated routes (like booking and viewing tickets).
Once you log out, your JWT token becomes invalid.

## ✅ Test Cases Included
```
Category          	Test Case
Auth       	Register success, Invalid email, Password strength, Duplicate user, Missing username
Login/Logout	Success, Incorrect password, User not found, Unauthorized logout
Movies	        Get Top 5 Titles, Get Paginated Movies
Seats	        Get Available Seats, Book Seats (Success, Seat already booked, Show not found)
OTP	        Send OTP, Verify OTP, Invalid OTP
Tickets  	View Latest Ticket (Success, No ticket found)
```
## 🧩 Output Verification

Each request contains Postman tests that automatically validate:

- Status codes

- Response messages

- Key fields (movie titles, booking info, etc.)

- Data consistency

You can see test results in the Test Results tab after running the collection.

## 🏁 Best Practice

- Always login first and ensure your token is saved.

- Use the same {{email}} and {{password}} for register/login to keep your session valid.

- Test sequentially to avoid token expiry.

- Logout only after all authenticated endpoints have been successfully tested. 