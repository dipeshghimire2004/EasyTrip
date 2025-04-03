import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { useState } from "react";
import Signup from "./components/SignUp/signup";
import Login from "./components/Login/login";
import HotelBookingUI from "./components/Booking/BookingApp";
import Payment from './components/ClientPanel/Payment/payment';
import AdminPanel from './components/AdminPanel/AdminPanel';
import Home from "./components/Home/Home";
import AboutUs from "./components/AboutUs/AboutUs"
import AddGuestHouseForm from "./components/HotelManagerPanel/AddGuesthouse/AddGuestHouseForm";
import Contact from "./components/Contact/Contact";

function ProtectedRoute({ element, userRole, allowedRoles }) {
  if (!allowedRoles.includes(userRole)) {
    alert("Login as user to access this page"); // Show alert before redirecting
    return <Navigate to="/" />;
  }
  return element;
}

function App() {
  const [userRole, setUserRole] = useState(null); // Track user role

  return (
    <Router>
      <Routes>
        {/* All */}
        <Route path="/" element={<Home />} />
        <Route path="/booking" element={<HotelBookingUI />} />
        <Route path="/about" element={<AboutUs />} />
        <Route path="/contact" element={<Contact />} />

        {/* User Routes */}
        <Route path="/CLIENT/signup" element={<Signup role="CLIENT" />} />
        <Route path="/CLIENT/login" element={<Login role="CLIENT" setUserRole={setUserRole} />} />
        <Route path="/CLIENT/payment" element={<ProtectedRoute element={<Payment />} userRole={userRole} allowedRoles={["CLIENT"]} />} />

        {/* Admin Routes */}
        <Route path="/ADMIN/login" element={<Login role="ADMIN" setUserRole={setUserRole} />} />
        <Route path="/ADMIN/*" element={<ProtectedRoute element={<AdminPanel />} userRole={userRole} allowedRoles={["ADMIN"]} />} />

        {/* Guesthouse Owner Routes */}
        <Route path="/HOTEL_MANAGER/signup" element={<Signup role="HOTEL_MANAGER" />} />
        <Route path="/HOTEL_MANAGER/login" element={<Login role="HOTEL_MANAGER" setUserRole={setUserRole} />} />
        <Route path="/HOTEL_MANAGER/addGuestHouse" element={<ProtectedRoute element={<AddGuestHouseForm />} userRole={userRole} allowedRoles={["HOTEL_MANAGER"]} />} />
      </Routes>
    </Router>
  );
}

export default App;
