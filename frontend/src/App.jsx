import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { useState } from "react";

// All
import Signup from "./components/SignUp/signup";
import Login from "./components/Login/Login";
import Home from "./components/Home/Home";
import AboutUs from "./components/AboutUs/AboutUs"
import Contact from "./components/Contact/Contact";
import HotelBookingUI from "./components/Booking/BookingApp";
import BusSearch from "./components/ClientPanel/BusSearch/BusSearch";

// Users
import BusBookingPayment from "./components/ClientPanel/BusBookingPayment/BusBookingPayment";
import Payment from './components/ClientPanel/Payment/Payment';
import MyBookings from "./components/ClientPanel/MyBooking/MyBookings";
import FindRoom from "./components/Booking/Room/FindRoom";

// Admin
import AdminPanel from './components/AdminPanel/AdminPanel';

// Guesthouse Owners
import AddGuestHouseForm from "./components/HotelManagerPanel/AddGuesthouse/AddGuestHouseForm";
import GuestHouseOverview from "./components/HotelManagerPanel/GuestHouseOverview/GuestHouseOverview";
import GuestHouseDashboard from "./components/HotelManagerPanel/Dashboard/Dashboard";
import CreateRoom from "./components/HotelManagerPanel/CreateRoom/CreateRoom";

// Bus Owners
import BusOwnerSignUp from "./components/Bus/BusOwnerSignUp/BusOwnerSignUp";
import BusDashboard from "./components/Bus/BusDashboard/BusDashboard";
import BusListing from "./components/Bus/BusListing/BusListing";
import UpdateAvailability from "./components/Bus/UpdateAvailability/UpdateAvailability";
import ManageBuses from "./components/Bus/ManageBuses/ManageBuses";

function ProtectedRoute({ element, userRole, allowedRoles }) {
  if (!allowedRoles.includes(userRole)) {
    // alert(`Login as ${allowedRoles.join(", ")} user to access this page`); 
    alert("Login as user to access this page"); // Show alert before redirecting
    return <Navigate to="/" />;
  }
  return element;
}

function App() {
  const [userRole, setUserRole] = useState(() => {
    // Read initial role from localStorage
    return localStorage.getItem("userRole") || null;
  });

  return (
    <Router>
      <Routes>

        {/* All */}
        <Route path="/" element={<Home />} />
        <Route path="/booking" element={<HotelBookingUI />} />
        <Route path="/about" element={<AboutUs />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/busSearch" element={<BusSearch />} />

        {/* User Routes */}
        <Route path="/CLIENT/signup" element={<Signup role="CLIENT" />} />
        <Route path="/CLIENT/login" element={<Login role="CLIENT" setUserRole={setUserRole} />} />
        <Route path="/CLIENT/findRoom" element={<ProtectedRoute element={<FindRoom />} userRole={userRole} allowedRoles={["CLIENT"]} />} />
        <Route path="/CLIENT/payment" element={<ProtectedRoute element={<Payment />} userRole={userRole} allowedRoles={["CLIENT"]} />} />
        <Route path="/CLIENT/busPayment" element={<ProtectedRoute element={<BusBookingPayment />} userRole={userRole} allowedRoles={["CLIENT"]} />} />
        <Route path="/CLIENT/Booked" element={<ProtectedRoute element={<MyBookings />} userRole={userRole} allowedRoles={["CLIENT"]} />} />

        {/* Admin Routes */}
        <Route path="/ADMIN/login" element={<Login role="ADMIN" setUserRole={setUserRole} />} />
        <Route path="/ADMIN/*" element={<ProtectedRoute element={<AdminPanel />} userRole={userRole} allowedRoles={["ADMIN"]} />} />

        {/* Guesthouse Owner Routes */}
        <Route path="/HOTEL_MANAGER/signup" element={<Signup role="HOTEL_MANAGER" />} />
        <Route path="/HOTEL_MANAGER/login" element={<Login role="HOTEL_MANAGER" setUserRole={setUserRole} />} />
        <Route path="/HOTEL_MANAGER/dashboard" element={<ProtectedRoute element={<GuestHouseDashboard />} userRole={userRole} allowedRoles={["HOTEL_MANAGER"]} />} />
        <Route path="/HOTEL_MANAGER/addGuestHouse" element={<ProtectedRoute element={<AddGuestHouseForm />} userRole={userRole} allowedRoles={["HOTEL_MANAGER"]} />} />
        <Route path="/HOTEL_MANAGER/guestHouseOverview" element={<ProtectedRoute element={<GuestHouseOverview />} userRole={userRole} allowedRoles={["HOTEL_MANAGER"]} />} />
        <Route path="/HOTEL_MANAGER/createRoom" element={<ProtectedRoute element={<CreateRoom />} userRole={userRole} allowedRoles={["HOTEL_MANAGER"]} />} />

        {/* BUS OPERATOR Routes */}
        <Route path="/BUS_OPERATOR/signup" element={<Signup role="BUS_OPERATOR" />} />
        <Route path="/BUS_OPERATOR/login" element={<Login role="BUS_OPERATOR" setUserRole={setUserRole} />} />
        <Route path="/BUS_OPERATOR/dashboard" element={<BusDashboard role="BUS_OPERATOR" />} />
        <Route path="/BUS_OPERATOR/listing" element={<BusListing role="BUS_OPERATOR" />} />
        <Route path="/BUS_OPERATOR/booking" element={<BusListing role="BUS_OPERATOR" />} />
        <Route path="/BUS_OPERATOR/updateAvailability" element={<UpdateAvailability role="BUS_OPERATOR" />} />
        <Route path="/BUS_OPERATOR/manageBuses" element={<ManageBuses role="BUS_OPERATOR" />} />
      </Routes>
    </Router>
  );
}

export default App;
