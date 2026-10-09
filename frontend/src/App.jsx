import { useEffect, useState } from "react";
import "./App.css";

import Projects from "./pages/Projects";
import Dataset from "./pages/Dataset";
import Model from "./pages/Model";
import Experiment from "./pages/Experiment";
import ResearchNotes from "./pages/ResearchNotes";

import Login from "./pages/Login";
import Signup from "./pages/Signup";

function App() {
  const [currentPage, setCurrentPage] = useState("dashboard");
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [showSignup, setShowSignup] = useState(false);
  const [userId, setUserId] = useState(null);
    const [dashboardData, setDashboardData] = useState({
    projects: 0,
    datasets: 0,
    models: 0,
    experiments: 0
  });

  const fetchDashboardData = async () => {
    try {
      const [projects, datasets, models, experiments] = await Promise.all([
        fetch("http://localhost:8080/AI-RnD-Workspace/projects"),
        fetch("http://localhost:8080/AI-RnD-Workspace/datasets"),
        fetch("http://localhost:8080/AI-RnD-Workspace/model-trainings"),
        fetch("http://localhost:8080/AI-RnD-Workspace/experiments")
      ]);

      const projectData = await projects.text();
      const datasetData = await datasets.text();
      const modelData = await models.text();
      const experimentData = await experiments.text();

      setDashboardData({
        projects: projectData.includes("No projects found")
          ? 0
          : projectData.split("\n").filter(Boolean).length,
        datasets: datasetData.includes("No datasets found")
          ? 0
          : datasetData.split("\n").filter(Boolean).length,
        models: modelData.includes("No model trainings found")
          ? 0
          : modelData.split("\n").filter(Boolean).length,
        experiments: experimentData.includes("No experiments found")
          ? 0
          : experimentData.split("\n").filter(Boolean).length
      });
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (isLoggedIn) {
      fetchDashboardData();
    }
  }, [isLoggedIn]);

  // =========================
  // LOGIN / SIGNUP
  // =========================
  if (!isLoggedIn) {
    if (showSignup) {
      return (
        <Signup
          onSignup={() => setIsLoggedIn(true)}
          onBackToLogin={() => setShowSignup(false)}
        />
      );
    }

    return (
     <Login
  onLogin={(id) => {
    setUserId(id);
    setIsLoggedIn(true);
  }}
  onSignup={() => setShowSignup(true)}
/>
    );
  }

  // =========================
  // MAIN APP
  // =========================
  return (
    <div className="app">

      {/* ================= SIDEBAR ================= */}
      <aside className="sidebar">

        <div className="logo">
           AI Research and Development Workspace
        </div>

        <nav>

          {/* Dashboard */}
          <a
            className={currentPage === "dashboard" ? "active" : ""}
            onClick={() => setCurrentPage("dashboard")}
          >
            Dashboard
          </a>

          {/* Projects */}
          <a
            className={currentPage === "projects" ? "active" : ""}
            onClick={() => setCurrentPage("projects")}
          >
            Projects
          </a>

          {/* Datasets */}
          <a
            className={currentPage === "dataset" ? "active" : ""}
            onClick={() => setCurrentPage("dataset")}
          >
            Datasets
          </a>

          {/* Models */}
          <a
            className={currentPage === "models" ? "active" : ""}
            onClick={() => setCurrentPage("models")}
          >
            Models
          </a>

          {/* Experiments */}
          <a
            className={currentPage === "experiments" ? "active" : ""}
            onClick={() => setCurrentPage("experiments")}
          >
            Experiments
          </a>

          {/* Research Notes */}
          <a
            className={currentPage === "researchNotes" ? "active" : ""}
            onClick={() => setCurrentPage("researchNotes")}
          >
            Research Notes
          </a>

        </nav>

        {/* Sidebar Bottom */}
        <div className="sidebar-bottom">
          <a>Settings</a>
        </div>

      </aside>


      {/* ================= MAIN CONTENT ================= */}

      <main className="main">

        {/* PROJECTS */}
        {currentPage === "projects" ? (

         <Projects userId={userId} />

        ) : currentPage === "dataset" ? (

          <Dataset userId={userId} />

        ) : currentPage === "models" ? (

          <Model userId={userId} />

        ) : currentPage === "experiments" ? (

          <Experiment userId={userId} />

        ) : currentPage === "researchNotes" ? (

          <ResearchNotes userId={userId}/>

        ) : (

          // ================= DASHBOARD =================

          <>

            {/* Header */}
            <header className="header">

              <div>
                <h1>MSAR AI</h1>

                <p>
                  Manage your AI research and development projects in one place.
                </p>
              </div>

              <button className="profile">
                SR
              </button>

            </header>


            {/* Welcome */}
            <section className="welcome">

              <div>

                <h2>
                  Welcome back, Researcher 👋
                </h2>

                <p>
                  Track your projects, datasets, models and experiments.
                </p>

              </div>

              <button className="primary-btn">
                + New Project
              </button>

            </section>


            {/* Statistics */}
            <section className="stats">

              <div className="card">

                <span>Projects</span>

                <h2>{dashboardData.projects}</h2>

                <p>
                  Active research projects
                </p>

              </div>


              <div className="card">

                <span>Datasets</span>

                <h2>{dashboardData.datasets}</h2>

                <p>
                  Available datasets
                </p>

              </div>


              <div className="card">

                <span>Models</span>

                <h2>{dashboardData.models}</h2>

                <p>
                  Trained AI models
                </p>

              </div>


              <div className="card">

                <span>Experiments</span>

                <h2>{dashboardData.experiments}</h2>

                <p>
                  Total experiments
                </p>

              </div>

            </section>


            {/* Research Overview */}
            <section className="research-overview">

              <div className="overview-header">

                <div>

                  <h2>
                    Research Overview
                  </h2>

                  <p>
                    Monitor your overall research progress.
                  </p>

                </div>

              </div>


              <div className="overview-grid">

                <div className="overview-card">

                  <span>
                    Project Progress
                  </span>

                  <strong>
                    72%
                  </strong>

                  <div className="overview-bar">

                    <div
                      className="overview-fill"
                      style={{ width: "72%" }}
                    ></div>

                  </div>

                  <small>
                    8 of 12 projects progressing
                  </small>

                </div>


                <div className="overview-card">

                  <span>
                    Experiments
                  </span>

                  <strong>
                    46
                  </strong>

                  <small>
                    12 experiments completed this month
                  </small>

                </div>


                <div className="overview-card">

                  <span>
                    Research Notes
                  </span>

                  <strong>
                    24
                  </strong>

                  <small>
                    6 notes added this week
                  </small>

                </div>

              </div>

            </section>


            {/* Quick Actions */}
            <section className="quick-actions">

              <div className="quick-actions-header">

                <div>

                  <h2>
                    Quick Actions
                  </h2>

                  <p>
                    Start your next research task.
                  </p>

                </div>

              </div>


              <div className="quick-actions-grid">


                {/* New Project */}
                <button
                  className="quick-action-card"
                  onClick={() => setCurrentPage("projects")}
                >

                  <span className="quick-action-icon">
                    ＋
                  </span>

                  <div>

                    <strong>
                      New Project
                    </strong>

                    <small>
                      Create a new research project
                    </small>

                  </div>

                </button>


                {/* Dataset */}
                <button
                  className="quick-action-card"
                  onClick={() => setCurrentPage("dataset")}
                >

                  <span className="quick-action-icon">
                    📊
                  </span>

                  <div>

                    <strong>
                      Analyze Dataset
                    </strong>

                    <small>
                      Explore and analyze your data
                    </small>

                  </div>

                </button>


                {/* Model */}
                <button
                  className="quick-action-card"
                  onClick={() => setCurrentPage("models")}
                >

                  <span className="quick-action-icon">
                    🤖
                  </span>

                  <div>

                    <strong>
                      Train Model
                    </strong>

                    <small>
                      Start a machine learning model
                    </small>

                  </div>

                </button>


                {/* Experiment */}
                <button
                  className="quick-action-card"
                  onClick={() => setCurrentPage("experiments")}
                >

                  <span className="quick-action-icon">
                    🧪
                  </span>

                  <div>

                    <strong>
                      Run Experiment
                    </strong>

                    <small>
                      Launch a new experiment
                    </small>

                  </div>

                </button>

              </div>

            </section>


            {/* Recent Projects */}
            <section className="content-card">

              <div className="section-header">

                <div>

                  <h2>
                    Recent Projects
                  </h2>

                  <p>
                    Your latest research projects
                  </p>

                </div>

                <button
                  className="view-btn"
                  onClick={() => setCurrentPage("projects")}
                >
                  View All
                </button>

              </div>


              <div className="project-list">


                <div className="project">

                  <div>

                    <h3>
                      Medical Image Classification
                    </h3>

                    <p>
                      Computer Vision • Updated 2 hours ago
                    </p>

                  </div>

                  <span className="status active-status">
                    Active
                  </span>

                </div>


                <div className="project">

                  <div>

                    <h3>
                      Natural Language Processing
                    </h3>

                    <p>
                      NLP • Updated yesterday
                    </p>

                  </div>

                  <span className="status">
                    Research
                  </span>

                </div>


                <div className="project">

                  <div>

                    <h3>
                      Recommendation System
                    </h3>

                    <p>
                      Machine Learning • Updated 3 days ago
                    </p>

                  </div>

                  <span className="status completed">
                    Completed
                  </span>

                </div>


              </div>

            </section>

          </>

        )}

      </main>

    </div>
  );
}

export default App;