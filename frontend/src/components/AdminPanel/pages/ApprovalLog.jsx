import React from "react";
import ActionButton from "../components/ActionButton";

const ApprovalLog = () => {
  const approvalData = [
    "Cozy Cottage",
    "Beachfront Villa",
    "Mountain Retreat",
    "Urban Loft",
    "Lakeview Cabin",
  ];

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
          {approvalData.map((house, index) => (
            <tr key={index} className="border-t">
              <td className="p-3">{house}</td>
              <td className="p-3">Pending</td>
              <td className="p-3">
                <ActionButton
                  label="Accept"
                  type="accept"
                  onClick={() => alert(`Accepted ${house}`)}
                />
                <ActionButton
                  label="Reject"
                  type="reject"
                  onClick={() => alert(`Rejected ${house}`)}
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
