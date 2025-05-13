import React, { useState, useEffect } from "react";
import ActionButton from "../components/ActionButton";
import api from "../../API/Api"; // Axios instance

const BusApprovalLog = ({ limit }) => {
  const [busData, setBusData] = useState([]);

  useEffect(() => {
    const fetchBusData = async () => {
      try {
        const response = await api.get("http://localhost:8021/api/buses/pending");
        setBusData(response.data);
      } catch (error) {
        console.error("Error fetching bus approval data:", error);
      }
    };

    fetchBusData();
  }, []);

  const displayedData = limit ? busData.slice(0, limit) : busData;

  const handleApprove = async (busId, name) => {
    try {
      await api.put(`api/buses/${busId}/approve?adminId=2`);
      alert(`Approved ${name}`);
      const response = await api.get("api/buses/pending");
      setBusData(response.data);
    } catch (error) {
      console.error("Error approving bus:", error);
      alert(`Failed to approve ${name}`);
    }
  };

  const handleReject = async (busId, name) => {
    try {
      await api.put(`api/buses/${busId}/deny?adminId=2`);
      alert(`Rejected ${name}`);
      const response = await api.get("api/buses/pending");
      setBusData(response.data);
    } catch (error) {
      console.error("Error rejecting bus:", error);
      alert(`Failed to reject ${name}`);
    }
  };

  return (
    <div className="p-8">
      <h2 className="text-2xl font-bold mb-4">Bus Approval Requests</h2>
      <table className="min-w-full bg-white shadow rounded">
        <thead>
          <tr className="bg-gray-200 text-gray-700">
            <th className="w-1/3 p-3 text-left">Bus Name</th>
            <th className="w-1/3 p-3 text-left">Status</th>
            <th className="w-1/3 p-3 text-left">Actions</th>
          </tr>
        </thead>
        <tbody>
          {displayedData.map((bus) => (
            <tr key={bus.id} className="border-t">
              <td className="p-3">{bus.name}</td>
              <td className="p-3">{bus.status}</td>
              <td className="p-3">
                <ActionButton
                  label="Approve"
                  type="accept"
                  onClick={() => handleApprove(bus.id, bus.name)}
                />
                <ActionButton
                  label="Deny"
                  type="reject"
                  onClick={() => handleReject(bus.id, bus.name)}
                />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BusApprovalLog;
