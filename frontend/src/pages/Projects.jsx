import { useEffect, useState } from "react";
import "./Projects.css";

function Projects({ userId }) {
  const [search, setSearch] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [projectName, setProjectName] = useState("");
  const [projectCategory, setProjectCategory] = useState("");
  const [projectDescription, setProjectDescription] = useState("");
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadProjects = async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/AI-RnD-Workspace/projects"
      );

      const text = await response.text();

      if (text.includes("No projects found")) {
        setProjects([]);
        return;
      }

      const blocks = text.split("-------------------------");

const loadedProjects = blocks
  .map((block) => {
    const id = block.match(/Project ID: (.*)/)?.[1]?.trim();
    const name = block.match(/Title: (.*)/)?.[1]?.trim();
    const description = block.match(/Description: (.*)/)?.[1]?.trim();
    const status = block.match(/Status: (.*)/)?.[1]?.trim();

    if (!id || !name) {
      return null;
    }

    return {
      id,
      name,
      category: description || "AI Research",
      status: status || "ACTIVE",
      progress: status === "COMPLETED" ? 100 : 0
    };
  })
  .filter(Boolean);

setProjects(loadedProjects);

      setProjects(loadedProjects);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProjects();
  }, []);

  const handleCreateProject = async () => {
    if (!projectName || !projectCategory) {
      alert("Please enter project name and category");
      return;
    }

    try {
      const formData = new URLSearchParams();

      formData.append("title", projectName);
      formData.append(
        "description",
        `${projectCategory} - ${projectDescription}`
      );
      formData.append("createdBy", userId);

      const response = await fetch(
        "http://localhost:8080/AI-RnD-Workspace/projects",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          body: formData.toString()
        }
      );

      const result = await response.text();

      if (!response.ok || result.toLowerCase().includes("error")) {
        alert(result || "Project creation failed.");
        return;
      }

      alert("Project created successfully!");

      setProjectName("");
      setProjectCategory("");
      setProjectDescription("");
      setShowModal(false);

      loadProjects();
    } catch (error) {
      alert("Unable to connect to the backend.");
    }
  };

  const filteredProjects = projects.filter((project) =>
    project.name.toLowerCase().includes(search.toLowerCase())
  );

  const activeProjects = projects.filter(
    (project) => project.status.toUpperCase() === "ACTIVE"
  ).length;

  const completedProjects = projects.filter(
    (project) => project.status.toUpperCase() === "COMPLETED"
  ).length;

  return (
    <>
      {showModal && (
        <div className="modal-overlay">
          <div className="modal-box">
            <h2>Create New Project</h2>
            <p>Start a new AI research project.</p>

            <input
              type="text"
              placeholder="Project name"
              value={projectName}
              onChange={(e) => setProjectName(e.target.value)}
            />

            <input
              type="text"
              placeholder="Category"
              value={projectCategory}
              onChange={(e) => setProjectCategory(e.target.value)}
            />

            <textarea
              placeholder="Project description"
              value={projectDescription}
              onChange={(e) => setProjectDescription(e.target.value)}
            ></textarea>

            <div className="modal-actions">
              <button onClick={() => setShowModal(false)}>
                Cancel
              </button>

              <button onClick={handleCreateProject}>
                Create Project
              </button>
            </div>
          </div>
        </div>
      )}

      <div className="projects-page">
        <div className="projects-header">
          <div>
            <h1>Research Projects</h1>
            <p>Manage and track your AI research projects.</p>
          </div>

          <button
            className="new-project-btn"
            onClick={() => setShowModal(true)}
          >
            + New Project
          </button>
        </div>

        <div className="project-stats">
          <div className="project-stat-card">
            <h3>{projects.length}</h3>
            <p>Total Projects</p>
          </div>

          <div className="project-stat-card">
            <h3>{activeProjects}</h3>
            <p>Active Projects</p>
          </div>

          <div className="project-stat-card">
            <h3>{completedProjects}</h3>
            <p>Completed</p>
          </div>
        </div>

        <div className="projects-list">
          <h2>All Projects</h2>

          <input
            type="text"
            placeholder="Search projects..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="project-search"
          />

          {loading ? (
            <p>Loading projects...</p>
          ) : filteredProjects.length === 0 ? (
            <p>No projects found.</p>
          ) : (
            filteredProjects.map((project) => (
              <div className="project-card" key={project.id}>
                <div className="project-info">
                  <h3>{project.name}</h3>
                  <p>{project.category}</p>
                </div>

                <div className="project-progress">
                  <div className="progress-text">
                    <span>{project.status}</span>
                    <span>{project.progress}%</span>
                  </div>

                  <div className="progress-bar">
                    <div
                      className="progress-fill"
                      style={{ width: `${project.progress}%` }}
                    ></div>
                  </div>
                </div>

                <button
                  className="view-btn"
                  onClick={() =>
                    alert(
                      `Project: ${project.name}\nCategory: ${project.category}\nStatus: ${project.status}\nProgress: ${project.progress}%`
                    )
                  }
                >
                  View
                </button>
              </div>
            ))
          )}
        </div>
      </div>
    </>
  );
}

export default Projects;