import "./Login.css";
import { useState } from "react";

function Login({ onLogin, onSignup }) {
  const [showPassword, setShowPassword] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    setMessage("");
    setLoading(true);

    try {
      const formData = new URLSearchParams();
      formData.append("email", email);
      formData.append("password", password);

      const response = await fetch(
        "https://ai-rnd-workspace.onrender.com/login",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          credentials: "include",
          body: formData.toString()
        }
      );

      const result = await response.text();

      const userIdMatch = result.match(/User ID:\s*(\d+)/);
      const userId = userIdMatch ? Number(userIdMatch[1]) : null;

      if (response.ok && result.includes("Login successful!") && userId) {
    onLogin(userId);
      } else  {
        setMessage(result || "Invalid email or password!");
      }
    } catch (error) {
      setMessage("Unable to connect to the backend.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <div className="login-logo">
          🧠
        </div>

        <h1>MSAR AI</h1>

        <p className="login-subtitle">
          Sign in to continue your research
        </p>

        <form onSubmit={handleLogin}>
          <div className="form-group">
            <label>Email</label>

            <input
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="form-group">
            <label>Password</label>

            <div className="password-wrapper">
              <input
                type={showPassword ? "text" : "password"}
                placeholder="Enter your password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
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

          <div className="login-options">
            <label className="remember">
              <input type="checkbox" />
              Remember me
            </label>

            <a href="#">Forgot password?</a>
          </div>

          {message && <p>{message}</p>}

          <button type="submit" className="login-btn" disabled={loading}>
            {loading ? "Logging in..." : "Login"}
          </button>
        </form>

        <p className="signup-text">
          Don't have an account?{" "}
          <button
            type="button"
            onClick={onSignup}
            className="signup-link"
          >
            Sign up
          </button>
        </p>
      </div>
    </div>
  );
}

export default Login;