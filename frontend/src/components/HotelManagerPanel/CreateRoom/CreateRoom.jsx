import React, { useState } from "react";
import api from "../../API/Api";
import SidebarNavigation from "../SidebarNavigation/SidebarNavigation";

const CreateRoom = () => {
  const [hotelId, setHotelId] = useState("");
  const [roomNumber, setRoomNumber] = useState("");
  const [roomType, setRoomType] = useState("");
  const [pricePerNight, setPricePerNight] = useState("");
  const [capacity, setCapacity] = useState("");
  const [isAvailable, setIsAvailable] = useState(false); // Initially set to false, meaning not available

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      !hotelId ||
      !roomNumber ||
      !roomType ||
      pricePerNight === "" ||
      capacity === ""
    ) {
      alert("Please fill out all fields.");
      return;
    }

    const price = parseFloat(pricePerNight);
    const cap = parseInt(capacity);
    const roomNum = parseInt(roomNumber);

    if (
      isNaN(price) ||
      price < 0 ||
      isNaN(cap) ||
      cap <= 0 ||
      isNaN(roomNum) ||
      parseInt(hotelId) <= 0
    ) {
      alert("Enter valid non-negative numbers.");
      return;
    }

    const newRoom = {
      roomNumber: roomNum,
      roomType,
      pricePerNight: price,
      available: isAvailable,  // The checkbox state will determine if the room is available
      capacity: cap,
    };

    try {
      const res = await api.post(`api/guesthouses/${hotelId}/rooms`, newRoom);
      console.log("Room created successfully:", newRoom);
      console.log("response:", res.data);
      alert("Room created successfully!");
      clearForm();
    } catch (err) {
      console.error("Error creating room:", err);
      alert("Failed to create room.");
    }
  };

  const clearForm = () => {
    setHotelId("");
    setRoomNumber("");
    setRoomType("");
    setPricePerNight("");
    setCapacity("");
    setIsAvailable(false); // Reset the availability to false when clearing the form
  };

  return (
    <div className="flex min-h-screen bg-gray-100">
      <SidebarNavigation />

      <main className="flex-1 p-8">
        <div className="max-w-3xl mx-auto bg-white p-8 rounded-lg shadow">
          <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">
            Create New Room
          </h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Hotel ID
              </label>
              <input
                type="number"
                value={hotelId}
                onChange={(e) => setHotelId(e.target.value)}
                placeholder="Enter Hotel ID"
                className="w-full p-3 border border-gray-300 rounded-md shadow-sm focus:ring-2 focus:ring-blue-500"
                required
              />
            </div>

            <div className="mb-4">
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Room Number
              </label>
              <input
                type="number"
                value={roomNumber}
                onChange={(e) => setRoomNumber(e.target.value)}
                placeholder="Enter Room Number"
                className="w-full p-3 border border-gray-300 rounded-md shadow-sm focus:ring-2 focus:ring-blue-500"
                required
              />
            </div>

            <div className="mb-4">
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Room Type
              </label>
              <select
                value={roomType}
                onChange={(e) => setRoomType(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              >
                <option value="">Select Room Type</option>
                <option value="Single">Single</option>
                <option value="Double">Double</option>
                <option value="Suite">Suite</option>
              </select>
            </div>

            <div className="mb-4">
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Price per Night
              </label>
              <input
                type="number"
                value={pricePerNight}
                onChange={(e) => setPricePerNight(e.target.value)}
                min="0"
                placeholder="Enter price"
                className="w-full p-3 border border-gray-300 rounded-md shadow-sm focus:ring-2 focus:ring-blue-500"
                required
              />
            </div>

            <div className="mb-4">
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Capacity
              </label>
              <input
                type="number"
                value={capacity}
                onChange={(e) => setCapacity(e.target.value)}
                min="1"
                placeholder="Enter capacity"
                className="w-full p-3 border border-gray-300 rounded-md shadow-sm focus:ring-2 focus:ring-blue-500"
                required
              />
            </div>

            <div className="mb-4 flex items-center">
              <input
                type="checkbox"
                checked={isAvailable} // Reflect the current availability state
                onChange={(e) => setIsAvailable(e.target.checked)} // Update the state when the checkbox is clicked
                className="mr-2"
              />
              <label className="text-sm font-medium text-gray-700">
                Is Available
              </label>
            </div>

            <div className="text-center">
              <button
                type="submit"
                className="px-6 py-3 bg-blue-600 text-white font-semibold rounded-lg hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                Create Room
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

export default CreateRoom;
