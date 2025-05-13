import React, { useState, useEffect } from "react";
import ActionButton from "../components/ActionButton";
import api from "../../API/Api"; // Import the Axios instance

const ApprovalLog = ({ limit }) => {
  const [approvalData, setApprovalData] = useState([]);

  // Fetch data on component mount
  useEffect(() => {
    const fetchApprovalData = async () => {
      try {
        const response = await api.get("/api/guesthouses/admin/pending");
        setApprovalData(response.data);
      } catch (error) {
        console.error("Error fetching approval data:", error);
      }
    };

    fetchApprovalData();
  }, []);

  // Default to showing all data if limit is undefined or null
  const displayedData = limit ? approvalData.slice(0, limit) : approvalData;

  // Handle Accept button click
  const handleAccept = async (id, name) => {
    try {
      await api.put(`/api/guesthouses/${id}/approve`);
      alert(`Accepted ${name}`);
      // Re-fetch data to update the table after approval
      const response = await api.get("/api/guesthouses/admin/pending");
      setApprovalData(response.data);
    } catch (error) {
      console.error("Error accepting guesthouse:", error);
      alert(`Failed to accept ${name}`);
    }
  };

  // Handle Reject button click
  const handleReject = async (id, name) => {
    try {
      await api.post(`/api/guesthouses/.${id}/reject`);
      alert(`Rejected ${name}`);
      // Re-fetch data to update the table after rejection
      const response = await api.get("/api/guesthouses/admin/pending");
      setApprovalData(response.data);
    } catch (error) {
      console.error("Error rejecting guesthouse:", error);
      alert(`Failed to reject ${name}`);
    }
  };

  return (
    <div className="p-8">
      <h2 className="text-2xl font-bold mb-4">Approval Requests</h2>
      <table className="min-w-full bg-white shadow rounded">
        <thead>
          <tr className="bg-gray-200 text-gray-700">
            <th className="w-1/3 p-3 text-left">Guesthouse Name</th>
            <th className="w-1/3 p-3 text-left">Status</th>
            <th className="w-1/3 p-3 text-left">Actions</th>
          </tr>
        </thead>
        <tbody>
          {displayedData.map((house) => (
            <tr key={house.id} className="border-t">
              <td className="p-3">{house.name}</td>
              <td className="p-3">{house.status}</td>
              <td className="p-3">
                <ActionButton
                  label="Accept"
                  type="accept"
                  onClick={() => handleAccept(house.id, house.name)}
                />
                <ActionButton
                  label="Reject"
                  type="reject"
                  onClick={() => handleReject(house.id, house.name)}
                />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ApprovalLog;
