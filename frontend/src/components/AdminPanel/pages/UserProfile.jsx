const UserProfile = () => {
  // User data
  const user = {
    id: 123456,
    name: "John Doe",
    email: "john.doe@example.com",
    registrationDate: "January 1, 2020",
    status: "Active"
  };

  // Booking history data
  const bookings = [
    { guesthouse: "Sunny Retreat", checkin: "March 10, 2023", checkout: "March 15, 2023", status: "Confirmed" },
    { guesthouse: "Mountain Escape", checkin: "April 5, 2023", checkout: "April 10, 2023", status: "Pending" },
    { guesthouse: "Beachside Villa", checkin: "May 1, 2023", checkout: "May 7, 2023", status: "Canceled" },
    { guesthouse: "City Center Inn", checkin: "June 15, 2023", checkout: "June 20, 2023", status: "Confirmed" },
    { guesthouse: "Countryside Lodge", checkin: "July 10, 2023", checkout: "July 15, 2023", status: "Confirmed" }
  ];

  // Status color mapping
  const statusColors = {
    Confirmed: "#90EE90",
    Pending: "#FFD700",
    Canceled: "#FF6961"
  };

  return (
    <div style={{ padding: "20px", fontFamily: "Arial, sans-serif" }}>
      {/* User Information Section */}
      <div style={{ marginBottom: "30px" }}>
        <h2 style={{ color: "#333", borderBottom: "2px solid #eee", paddingBottom: "10px" }}>
          User Information
        </h2>
        <div style={{ lineHeight: "1.6" }}>
          <p><strong>User ID:</strong> {user.id}</p>
          <p><strong>Full Name:</strong> {user.name}</p>
          <p><strong>Email Address:</strong> {user.email}</p>
          <p><strong>Registration Date:</strong> {user.registrationDate}</p>
          <p><strong>Account Status:</strong> 
            <span style={{ 
              color: user.status === "Active" ? "green" : "red",
              marginLeft: "8px"
            }}>
              {user.status}
            </span>
          </p>
        </div>
      </div>

      {/* Booking History Section */}
      <div>
        <h2 style={{ color: "#333", borderBottom: "2px solid #eee", paddingBottom: "10px" }}>
          Booking History
        </h2>
        <table style={{ width: "100%", borderCollapse: "collapse" }}>
          <thead>
            <tr style={{ backgroundColor: "#f5f5f5" }}>
              <th style={{ padding: "12px", textAlign: "left", borderBottom: "2px solid #ddd" }}>Guesthouse Name</th>
              <th style={{ padding: "12px", textAlign: "left", borderBottom: "2px solid #ddd" }}>Check-in Date</th>
              <th style={{ padding: "12px", textAlign: "left", borderBottom: "2px solid #ddd" }}>Check-out Date</th>
              <th style={{ padding: "12px", textAlign: "left", borderBottom: "2px solid #ddd" }}>Booking Status</th>
            </tr>
          </thead>
          <tbody>
            {bookings.map((booking, index) => (
              <tr key={index} style={{ borderBottom: "1px solid #ddd" }}>
                <td style={{ padding: "12px" }}>{booking.guesthouse}</td>
                <td style={{ padding: "12px" }}>{booking.checkin}</td>
                <td style={{ padding: "12px" }}>{booking.checkout}</td>
                <td style={{ padding: "12px" }}>
                  <span style={{
                    backgroundColor: statusColors[booking.status],
                    padding: "4px 8px",
                    borderRadius: "4px",
                    color: "#333"
                  }}>
                    {booking.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default UserProfile;