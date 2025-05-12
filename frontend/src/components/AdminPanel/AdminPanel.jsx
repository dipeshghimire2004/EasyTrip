import React from "react";
import { Routes, Route, useLocation } from "react-router-dom";
import Sidebar from "./components/Sidebar";
import Dashboard from "./pages/Dashboard";
import BookingLog from "./pages/BookingLog";
import ApprovalLog from "./pages/ApprovalLog";
import UserAccounts from "./pages/UserAccounts";
import GuestHouseDetails from "./pages/GuestHouseDetails";
import GuestHouseListing from "./pages/GuestHouseListing";
import BusApprovalLog from "./pages/BusApprovalLog";

import UserProfile from "./pages/UserProfile";
import NotFound from "./pages/NotFound"; 

function AdminPanel() {
  const location = useLocation();

  const isNotFoundPage = location.pathname === "/admin/*";

  return (
    <div className="flex h-screen">
      {/* Only show Sidebar if not on the Not Found page */}
      {!isNotFoundPage && <Sidebar />}
      
      <main className="flex-1 bg-gray-100 overflow-auto">
        <Routes>
          <Route path="/panel" element={<Dashboard />} />
          <Route path="/userAccounts" element={<UserAccounts/>} />
          {/* <Route path="/userAccounts/Guesthouse Owner" element={<GuestHouseDetails/>} /> */}
          {/* <Route path="/userAccounts/user" element={<UserProfile/>} /> */}
          <Route path="/bookinglog" element={<BookingLog />} />
          <Route path="/approvallog" element={<ApprovalLog />} />
          <Route path="/busApprovallog" element={<BusApprovalLog />} />
          <Route path="/guestHouseListing" element={<GuestHouseListing />} />
          {/* Wildcard route to show the 'Page Not Found' message */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </main>
    </div>
  );
}

export default AdminPanel;
