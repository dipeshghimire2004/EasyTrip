import React, { useState, useEffect } from "react";
import api from "../../API/Api"; // Import the Axios instance

const GuestHouseListing = () => {
  const [guesthouses, setGuesthouses] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchGuesthouses = async () => {
      try {
        const response = await api.get("/api/guesthouses");
        setGuesthouses(response.data); // Store data in state
        setLoading(false); // Set loading to false once data is fetched
      } catch (error) {
        console.error("Error fetching guesthouses:", error);
        setLoading(false);
      }
    };

    fetchGuesthouses();
  }, []);

  if (loading) {
    return <div className="text-center p-4">Loading...</div>;
  }

  return (
    <div className="p-8">
      <h2 className="text-2xl font-bold mb-4">Guesthouse Listings</h2>
      <table className="min-w-full bg-white shadow rounded">
        <thead>
          <tr className="bg-gray-200 text-gray-700">
            <th className="p-3 text-left">ID</th>
            <th className="p-3 text-left">Name</th>
            <th className="p-3 text-left">Location</th>
            <th className="p-3 text-left">Description</th>
            <th className="p-3 text-left">Amenities</th>
            <th className="p-3 text-left">Price Per Night</th>
            <th className="p-3 text-left">Status</th>
            <th className="p-3 text-left">Contact</th>
          </tr>
        </thead>
        <tbody>
          {guesthouses.map((guesthouse) => (
            <tr key={guesthouse.id} className="border-t">
              <td className="p-3">{guesthouse.id}</td>
              <td className="p-3">{guesthouse.name}</td>
              <td className="p-3">{guesthouse.location}</td>
              <td className="p-3">{guesthouse.description}</td>
              <td className="p-3">{guesthouse.amenities}</td>
              <td className="p-3">${guesthouse.pricePerNight}</td>
              <td className="p-3">{guesthouse.status}</td>
              <td className="p-3">{guesthouse.contactDetails}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default GuestHouseListing;
