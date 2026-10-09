import { useEffect, useState } from "react";

function Experiments({ userId }) {
  const [experiments, setExperiments] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [projectId, setProjectId] = useState("");
  const [datasetId, setDatasetId] = useState("");
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [modelName, setModelName] = useState("");
  const [parameters, setParameters] = useState("");
  const [metrics, setMetrics] = useState("");
  const [loading, setLoading] = useState(true);

  const loadExperiments = async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/AI-RnD-Workspace/experiments"
      );

      const text = await response.text();

      if (text.includes("No experiments found")) {
        setExperiments([]);
        return;
      }

      const blocks = text.split("-------------------------");

      const loadedExperiments = blocks
        .map((block) => {
          const id = block.match(/Experiment ID: (.*)/)?.[1]?.trim();
          const experimentName = block.match(/Name: (.*)/)?.[1]?.trim();
          const experimentDescription = block
            .match(/Description: (.*)/)?.[1]
            ?.trim();
          const experimentModel = block
            .match(/Model Name: (.*)/)?.[1]
            ?.trim();
          const experimentMetrics = block
            .match(/Metrics: (.*)/)?.[1]
            ?.trim();
          const status = block.match(/Status: (.*)/)?.[1]?.trim();

          if (!id || !experimentName) {
            return null;
          }

          return {
            id,
            name: experimentName,
            description:
              experimentDescription || "AI research experiment",
            modelName: experimentModel || "AI Model",
            metrics: experimentMetrics || "Not available",
            status: status || "RESEARCH"
          };
        })
        .filter(Boolean);

      setExperiments(loadedExperiments);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadExperiments();
  }, []);

  const handleCreateExperiment = async () => {
    if (!projectId || !datasetId || !name) {
      alert("Please enter project ID, dataset ID and experiment name");
      return;
    }

    try {
      const formData = new URLSearchParams();

      formData.append("projectId", projectId);
      formData.append("datasetId", datasetId);
      formData.append("name", name);
      formData.append("description", description);
      formData.append("modelName", modelName);
      formData.append("parameters", parameters);
      formData.append("metrics", metrics);
      formData.append("status", "RUNNING");
      formData.append("createdBy", userId);

      const response = await fetch(
        "http://localhost:8080/AI-RnD-Workspace/experiments",
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
        alert(result || "Experiment creation failed.");
        return;
      }

      alert("Experiment created successfully!");

      setProjectId("");
      setDatasetId("");
      setName("");
      setDescription("");
      setModelName("");
      setParameters("");
      setMetrics("");
      setShowModal(false);

      loadExperiments();
    } catch (error) {
      alert("Unable to connect to the backend.");
    }
  };

  const runningExperiments = experiments.filter(
    (experiment) => experiment.status.toUpperCase() === "RUNNING"
  ).length;

  const completedExperiments = experiments.filter(
    (experiment) => experiment.status.toUpperCase() === "COMPLETED"
  ).length;

  const failedExperiments = experiments.filter(
    (experiment) => experiment.status.toUpperCase() === "FAILED"
  ).length;

  return (
    <>
      {showModal && (
        <div className="modal-overlay">
          <div className="modal-box">
            <h2>Create New Experiment</h2>
            <p>Start a new AI research experiment.</p>

            <input
              type="number"
              placeholder="Project ID"
              value={projectId}
              onChange={(e) => setProjectId(e.target.value)}
            />

            <input
              type="number"
              placeholder="Dataset ID"
              value={datasetId}
              onChange={(e) => setDatasetId(e.target.value)}
            />

            <input
              type="text"
              placeholder="Experiment name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />

            <input
              type="text"
              placeholder="Model name"
              value={modelName}
              onChange={(e) => setModelName(e.target.value)}
            />

            <input
              type="text"
              placeholder="Parameters"
              value={parameters}
              onChange={(e) => setParameters(e.target.value)}
            />

            <input
              type="text"
              placeholder="Metrics"
              value={metrics}
              onChange={(e) => setMetrics(e.target.value)}
            />

            <textarea
              placeholder="Experiment description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            ></textarea>

            <div className="modal-actions">
              <button onClick={() => setShowModal(false)}>
                Cancel
              </button>

              <button onClick={handleCreateExperiment}>
                Create Experiment
              </button>
            </div>
          </div>
        </div>
      )}

      <div className="page">
        <header className="header">
          <div>
            <h1>Experiments</h1>
            <p>Run and track your AI research experiments.</p>
          </div>

          <button
            className="primary-btn"
            onClick={() => setShowModal(true)}
          >
            + New Experiment
          </button>
        </header>

        <section className="stats">
          <div className="card">
            <span>Total Experiments</span>
            <h2>{experiments.length}</h2>
            <p>All experiments</p>
          </div>

          <div className="card">
            <span>Running</span>
            <h2>{runningExperiments}</h2>
            <p>Currently running</p>
          </div>

          <div className="card">
            <span>Completed</span>
            <h2>{completedExperiments}</h2>
            <p>Successfully completed</p>
          </div>

          <div className="card">
            <span>Failed</span>
            <h2>{failedExperiments}</h2>
            <p>Requires attention</p>
          </div>
        </section>

        <section className="content-card">
          <h2>Recent Experiments</h2>
          <p>Your latest research experiments</p>

          <div className="project-list">
            {loading ? (
              <p>Loading experiments...</p>
            ) : experiments.length === 0 ? (
              <p>No experiments found.</p>
            ) : (
              experiments.map((experiment) => (
                <div className="project" key={experiment.id}>
                  <div>
                    <h3>{experiment.name}</h3>
                    <p>
                      {experiment.modelName} • Metrics: {experiment.metrics}
                    </p>
                  </div>

                  <span
                    className={`status ${
                      experiment.status.toUpperCase() === "RUNNING"
                        ? "active-status"
                        : experiment.status.toUpperCase() === "COMPLETED"
                        ? "completed"
                        : ""
                    }`}
                  >
                    {experiment.status}
                  </span>
                </div>
              ))
            )}
          </div>
        </section>
      </div>
    </>
  );
}

export default Experiments;