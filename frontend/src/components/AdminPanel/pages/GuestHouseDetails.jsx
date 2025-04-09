import React from "react";

const GuestHouseDetails = () => {
  const guesthouseData = [
    {
      ownerName: "John Doe",
      email: "johndoe@example.com",
      registrationDate: "January 15, 2023",
      status: "Approved",
      guesthouses: [
        {
          name: "Sunny Retreat",
          images: ["150x100", "150x100"],
          description: "A cozy place to relax and unwind.",
          amenities: "WiFi, Pool, Breakfast",
          totalRooms: 10,
        },
        {
          name: "Sunny Retreat",
          images: ["150x100", "150x100"],
          description: "A cozy place to relax and unwind.",
          amenities: "WiFi, Pool, Breakfast",
          totalRooms: 10,
        },
      ],
    },
  ];

  return (
    <div className="p-6 bg-gray-900 min-h-screen text-white">
      <h2 className="text-2xl font-bold mb-4">Guesthouse Owner Details</h2>

      <div className="bg-gray-800 p-4 rounded-lg mb-6">
        <h3 className="text-lg font-semibold">Guesthouse Owner Details</h3>
        <p>
          <span className="font-semibold">Owner Name:</span> {guesthouseData[0].ownerName}
        </p>
        <p>
          <span className="font-semibold">Email Address:</span> {guesthouseData[0].email}
        </p>
        <p>
          <span className="font-semibold">Registration Date:</span> {guesthouseData[0].registrationDate}
        </p>
        <p>
          <span className="font-semibold">Account Status:</span> 
          <span className="text-green-400"> {guesthouseData[0].status}</span>
        </p>
      </div>

      {guesthouseData[0].guesthouses.map((house, index) => (
        <div key={index} className="bg-gray-800 p-4 rounded-lg mb-6">
          <h3 className="text-lg font-semibold">Guesthouse Information</h3>

          <label className="block mt-2">Guesthouse Name</label>
          <input
            type="text"
            value={house.name}
            className="w-full p-2 bg-gray-700 rounded mt-1 text-white"
            readOnly
          />

          <label className="block mt-4">Images</label>
          <div className="flex gap-2 mt-1">
            {house.images.map((img, i) => (
              <div key={i} className="w-[150px] h-[100px] bg-gray-600 flex items-center justify-center text-gray-300 rounded">
                {img}
              </div>
            ))}
          </div>

          <label className="block mt-4">Description</label>
          <textarea
            className="w-full p-2 bg-gray-700 rounded mt-1 text-white"
            readOnly
            rows="3"
          >
            {house.description}
          </textarea>

          <label className="block mt-4">Amenities (comma-separated)</label>
          <input
            type="text"
            value={house.amenities}
            className="w-full p-2 bg-gray-700 rounded mt-1 text-white"
            readOnly
          />

          <label className="block mt-4">Total Rooms Available</label>
          <input
            type="text"
            value={house.totalRooms}
            className="w-full p-2 bg-gray-700 rounded mt-1 text-white"
            readOnly
          />
        </div>
      ))}
    </div>
  );
};

export default GuestHouseDetails;
