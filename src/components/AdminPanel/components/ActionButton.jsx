import React from "react";

const ActionButton = ({ label, type, onClick }) => (
  <button
    onClick={onClick}
    className={`${
      type === "accept" ? "bg-green-500" : "bg-red-500"
    } text-white px-4 py-2 rounded mr-2 hover:opacity-90`}
  >
    {label}
  </button>
);

export default ActionButton;
