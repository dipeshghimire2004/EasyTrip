import React, { useState, useEffect } from "react";
import axios from "axios";
import api from "../../API/Api";

const UserAccounts = () => {
  const [users, setUsers] = useState([]);
  const [search, setSearch] = useState("");
  const [sortAscending, setSortAscending] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    console.log("Calling GET /api/admin/users (active users)");
    const fetchActiveUsers = api.get("/api/admin/users");

    console.log("Calling GET http://localhost:8021/api/auth (inactive users)");
    const fetchInactiveUsers = axios.get("http://localhost:8021/api/auth");

    Promise.all([fetchActiveUsers, fetchInactiveUsers])
      .then(([activeRes, inactiveRes]) => {
        const activeUsers = activeRes.data.map(user => ({ ...user, is_active: true }));
        const inactiveUsers = inactiveRes.data.map(user => ({ ...user, is_active: false }));
        const combinedUsers = [...activeUsers, ...inactiveUsers];
        console.log("Combined users:", combinedUsers);
        setUsers(combinedUsers);
        setError("");
      })
      .catch(error => {
        console.error("Error fetching users:", error);
        setError("Failed to load user data. Please try again later.");
      });
  }, []);

  const updateUserStatus = (id, status) => {
    const url = `/api/admin/users/${id}/${status ? "activate" : "deactivate"}`;
    console.log(`Calling POST ${url}`);
    api
      .post(url)
      .then(() => {
        console.log(`User ${id} ${status ? "activated" : "deactivated"} successfully`);
        setUsers(prevUsers =>
          prevUsers.map(user =>
            user.id === id ? { ...user, is_active: status } : user
          )
        );
        alert(status ? "User Activated" : "User Deactivated");
      })
      .catch(error => {
        console.error("Error updating user status:", error);
        alert("Failed to update user status. Please try again later.");
      });
  };

  const sortedUsers = [...users].sort((a, b) => {
    const aId = Number(a.id);
    const bId = Number(b.id);
    return sortAscending ? aId - bId : bId - aId;
  });

  return (
    <div className="p-6 bg-white min-h-screen text-black">
      <h2 className="text-2xl font-bold mb-4">
        User Accounts (Total: {users.length})
      </h2>

      {error && (
        <div className="mb-4 p-3 bg-red-500 text-white rounded">{error}</div>
      )}

      <div className="flex gap-2 mb-4">
        <input
          type="text"
          placeholder="Search by User ID, Name, Email"
          className="p-2 rounded bg-gray-100 text-black w-1/2"
          onChange={(e) => setSearch(e.target.value.toLowerCase())}
        />
        <button
          onClick={() => setSortAscending(!sortAscending)}
          className="p-2 bg-blue-600 rounded text-white"
        >
          Sorted by ID in {sortAscending ? "(Ascending)" : "(Descending)"} order.
        </button>
      </div>

      <div className="overflow-x-auto">
        <table className="w-full text-left border border-gray-700">
          <thead className="bg-gray-200">
            <tr>
              <th className="p-2">User ID</th>
              <th className="p-2">Full Name</th>
              <th className="p-2">Email Address</th>
              <th className="p-2">Status</th>
              <th className="p-2">Actions</th>
            </tr>
          </thead>
          <tbody>
            {sortedUsers
              .filter(user =>
                user.name.toLowerCase().includes(search) ||
                user.email.toLowerCase().includes(search) ||
                user.id.includes(search)
              )
              .map((user) => (
                <tr key={user.id} className="border-b border-gray-700">
                  <td className="p-2">{user.id}</td>
                  <td className="p-2">{user.name}</td>
                  <td className="p-2">{user.email}</td>
                  <td className="p-2">{user.is_active ? "Active" : "Inactive"}</td>
                  <td className="p-2 flex gap-2">
                    <button
                      onClick={() => updateUserStatus(user.id, true)}
                      className={`p-1 px-3 rounded text-white ${user.is_active ? 'bg-green-300 cursor-not-allowed' : 'bg-green-600'}`}
                      disabled={user.is_active}
                    >
                      Activate
                    </button>
                    <button
                      onClick={() => updateUserStatus(user.id, false)}
                      className={`p-1 px-3 rounded text-white ${!user.is_active ? 'bg-red-300 cursor-not-allowed' : 'bg-red-600'}`}
                      disabled={!user.is_active}
                    >
                      Deactivate
                    </button>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default UserAccounts;
