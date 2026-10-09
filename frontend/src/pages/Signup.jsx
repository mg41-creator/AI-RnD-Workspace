import "./Signup.css";
import { useState } from "react";

function Signup({ onSignup, onBackToLogin }) {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSignup = async (e) => {
    e.preventDefault();
    setMessage("");

    const formData = new FormData(e.target);
    const password = formData.get("password");
    const confirmPassword = formData.get("confirmPassword");

    if (password !== confirmPassword) {
      setMessage("Passwords do not match!");
      return;
    }

    setLoading(true);

    try {
      formData.set("role", "RESEARCHER");
      formData.delete("confirmPassword");

      const response = await fetch(
        "http://localhost:8080/AI-RnD-Workspace/register",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          body: new URLSearchParams(formData)
        }
      );

      const result = await response.text();

      if (response.ok && result.toLowerCase().includes("success")) {
        alert("Account created successfully! 🎉");
        onBackToLogin();
      } else {
        setMessage(result || "Signup failed.");
      }
    } catch (error) {
      setMessage("Unable to connect to the backend.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="signup-page">
      <div className="signup-card">
        <div className="signup-logo">
          🧠
        </div>

        <h1>Create New Account</h1>

        <p className="signup-subtitle">
          Join MSAR AI to manage your research projects and datasets. 
        </p>

        <form onSubmit={handleSignup}>
          <div className="form-group">
            <label>Full Name</label>
            <input
              type="text"
              name="name"
              placeholder="Enter your name"
              required
            />
          </div>

          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              name="email"
              placeholder="Enter your email"
              required
            />
          </div>

          <div className="form-group">
            <label>Password</label>
            <div className="password-wrapper">
              <input
                type={showPassword ? "text" : "password"}
                name="password"
                placeholder="Create a password"
                required
              />
              <button
                type="button"
                className="show-password"
                onClick={() => setShowPassword(!showPassword)}
              >
                {showPassword ? "🙈" : "👁️"}
              </button>
            </div>
          </div>

          <div className="form-group">
            <label>Confirm Password</label>
            <div className="password-wrapper">
              <input
                type={showConfirmPassword ? "text" : "password"}
                name="confirmPassword"
                placeholder="Confirm your password"
                required
              />
              <button
                type="button"
                className="show-password"
                onClick={() => setShowConfirmPassword(!showConfirmPassword)}
              >
                {showConfirmPassword ? "🙈" : "👁️"}
              </button>
            </div>
          </div>

          {message && <p>{message}</p>}

          <button type="submit" className="signup-btn" disabled={loading}>
            {loading ? "Creating Account..." : "Create Account"}
          </button>
        </form>

        <button
          type="button"
          className="back-login-btn"
          onClick={onBackToLogin}
        >
          ← Back to Login
        </button>

        <p className="login-text">
          Already have an account?{" "}
          <a
            href="#"
            onClick={(e) => {
              e.preventDefault();
              onSignup();
            }}
          >
            Login
          </a>
        </p>
      </div>
    </div>
  );
}

export default Signup;