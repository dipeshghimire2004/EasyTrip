import React from "react";

const BusListing = () => {
  const buses = [
    {
      model: "Volvo 9400",
      registration: "KA01AB1234",
      capacity: 45,
      area: "Pokhara",
      status: "Active",
      date: "2024-01-15",
    },
    {
      model: "Mercedes Benz OH",
      registration: "KA02CD5678",
      capacity: 52,
      area: "kathmandu",
      status: "Inactive",
      date: "2024-01-10",
    },
  ];

  return (
    <div className="p-6 max-w-5xl mx-auto bg-white rounded-xl shadow-md">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold">Bus Listing Management</h2>
        <button className="bg-blue-500 text-white px-4 py-2 rounded">Add New Bus</button>
      </div>
      <input
        type="text"
        placeholder="Search buses..."
        className="w-full p-2 mb-4 border border-gray-300 rounded"
      />
      <table className="w-full text-left border-collapse">
        <thead>
          <tr className="bg-gray-100">
            <th className="p-2">Bus Model</th>
            <th className="p-2">Registration</th>
            <th className="p-2">Capacity</th>
            <th className="p-2">Service Area</th>
            <th className="p-2">Status</th>
            <th className="p-2">Date Added</th>
            <th className="p-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {buses.map((bus, index) => (
            <tr key={index} className="border-b">
              <td className="p-2">{bus.model}</td>
              <td className="p-2">{bus.registration}</td>
              <td className="p-2">{bus.capacity}</td>
              <td className="p-2">{bus.area}</td>
              <td className="p-2">
                <span
                  className={`px-3 py-1 text-sm rounded-full font-medium ${
                    bus.status === "Active"
                      ? "bg-green-200 text-green-800"
                      : "bg-red-200 text-red-800"
                  }`}
                >
                  {bus.status}
                </span>
              </td>
              <td className="p-2">{bus.date}</td>
              <td className="p-2">
                <button className="bg-red-500 text-white px-3 py-1 rounded">
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BusListing;
