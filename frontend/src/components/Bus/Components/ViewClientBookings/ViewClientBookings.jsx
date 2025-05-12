import React, { useState, useEffect } from "react";
import api from "../../../API/Api"; // Import the Axios instance

const ViewClientBookings = () => {
  const [clientBookings, setClientBookings] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchClientBookings = async () => {
      try {
        const response = await api.get("/api/buses/bookings");
        setClientBookings(response.data); // Store data in state
        setLoading(false); // Set loading to false once data is fetched
      } catch (error) {
        console.error("Error fetching client bookings:", error);
        setLoading(false);
      }
    };

    fetchClientBookings();
  }, []);

  if (loading) {
    return <div className="text-center p-4">Loading...</div>;
  }

  return (
    <div className="p-8">
      <h2 className="text-2xl font-bold mb-4">Client Bookings</h2>
      <table className="min-w-full bg-white shadow rounded">
        <thead>
          <tr className="bg-gray-200 text-gray-700">
            <th className="p-3 text-left">Booking ID</th>
            <th className="p-3 text-left">Client ID</th>
            <th className="p-3 text-left">Bus ID</th>
            <th className="p-3 text-left">Booking Time</th>
            <th className="p-3 text-left">Seats Booked</th>
            <th className="p-3 text-left">Payment Method</th>
            <th className="p-3 text-left">Cancelled</th>
          </tr>
        </thead>
        <tbody>
          {clientBookings.map((booking) => (
            <tr key={booking.id} className="border-t">
              <td className="p-3">{booking.id}</td>
              <td className="p-3">{booking.clientId}</td>
              <td className="p-3">{booking.busId}</td>
              <td className="p-3">{new Date(booking.bookingTime).toLocaleString()}</td>
              <td className="p-3">{booking.seatsBooked}</td>
              <td className="p-3">{booking.paymentMethod}</td>
              <td className="p-3">{booking.cancelled ? "Yes" : "No"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ViewClientBookings;
