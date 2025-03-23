import { useState } from 'react';
import './App.css';
import './index.css';

import { BrowserRouter as Router, Routes, Route, useNavigate } from "react-router-dom";
import Signup from "./components/signup";
import Login from "./components/login";

function App() {
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [email, setEmail] = useState("");

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Signup />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </Router>
  );
}

export default App;
