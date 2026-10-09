import { useEffect, useState } from "react";

function Models({ userId }) {
  const [models, setModels] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [modelName, setModelName] = useState("");
  const [algorithm, setAlgorithm] = useState("");
  const [parameters, setParameters] = useState("");
  const [accuracy, setAccuracy] = useState("");
  const [loss, setLoss] = useState("");
  const [projectId, setProjectId] = useState("");
  const [datasetId, setDatasetId] = useState("");
  const [experimentId, setExperimentId] = useState("");
  const [loading, setLoading] = useState(true);

  const loadModels = async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/AI-RnD-Workspace/model-trainings"
      );

      const text = await response.text();

      if (text.includes("No model trainings found")) {
        setModels([]);
        return;
      }

      const blocks = text.split("-------------------------");

      const loadedModels = blocks
        .map((block) => {
          const id = block.match(/Training ID: (.*)/)?.[1]?.trim();
          const name = block.match(/Model Name: (.*)/)?.[1]?.trim();
          const modelAlgorithm = block
            .match(/Algorithm: (.*)/)?.[1]
            ?.trim();
          const status = block.match(/Status: (.*)/)?.[1]?.trim();
          const modelAccuracy = block
            .match(/Accuracy: (.*)/)?.[1]
            ?.trim();

          if (!id || !name) {
            return null;
          }

          return {
            id,
            name,
            algorithm: modelAlgorithm || "Machine Learning",
            status: status || "TRAINED",
            accuracy: modelAccuracy || "0"
          };
        })
        .filter(Boolean);

      setModels(loadedModels);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadModels();
  }, []);

  const handleCreateModel = async () => {
    if (
      !modelName ||
      !algorithm ||
      !projectId ||
      !datasetId ||
      !experimentId
    ) {
      alert("Please enter all required model details");
      return;
    }

    try {
      const formData = new URLSearchParams();

      formData.append("projectId", projectId);
      formData.append("datasetId", datasetId);
      formData.append("experimentId", experimentId);
      formData.append("modelName", modelName);
      formData.append("algorithm", algorithm);
      formData.append("parameters", parameters);
      formData.append("accuracy", accuracy || "0");
      formData.append("loss", loss || "0");
      formData.append("status", "TRAINED");
      formData.append("startedBy", userId);

      const response = await fetch(
        "http://localhost:8080/AI-RnD-Workspace/model-trainings",
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
        alert(result || "Model creation failed.");
        return;
      }

      alert("Model created successfully!");

      setModelName("");
      setAlgorithm("");
      setParameters("");
      setAccuracy("");
      setLoss("");
      setProjectId("");
      setDatasetId("");
      setExperimentId("");
      setShowModal(false);

      loadModels();
    } catch (error) {
      alert("Unable to connect to the backend.");
    }
  };

  const trainedModels = models.filter(
    (model) => model.status.toUpperCase() === "TRAINED"
  ).length;

  const fineTunedModels = models.filter(
    (model) => model.status.toUpperCase() === "FINE-TUNED"
  ).length;

  return (
    <>
      {showModal && (
        <div className="modal-overlay">
          <div className="modal-box">
            <h2>Create New Model</h2>
            <p>Add a trained model to your research workspace.</p>

            <input
              type="text"
              placeholder="Model name"
              value={modelName}
              onChange={(e) => setModelName(e.target.value)}
            />

            <input
              type="text"
              placeholder="Algorithm"
              value={algorithm}
              onChange={(e) => setAlgorithm(e.target.value)}
            />

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
              type="number"
              placeholder="Experiment ID"
              value={experimentId}
              onChange={(e) => setExperimentId(e.target.value)}
            />

            <input
              type="text"
              placeholder="Parameters"
              value={parameters}
              onChange={(e) => setParameters(e.target.value)}
            />

            <input
              type="number"
              step="0.01"
              placeholder="Accuracy"
              value={accuracy}
              onChange={(e) => setAccuracy(e.target.value)}
            />

            <input
              type="number"
              step="0.01"
              placeholder="Loss"
              value={loss}
              onChange={(e) => setLoss(e.target.value)}
            />

            <div className="modal-actions">
              <button onClick={() => setShowModal(false)}>
                Cancel
              </button>

              <button onClick={handleCreateModel}>
                Create Model
              </button>
            </div>
          </div>
        </div>
      )}

      <div className="page">
        <header className="header">
          <div>
            <h1>AI Models</h1>
            <p>Manage and explore your AI research models.</p>
          </div>

          <button
            className="primary-btn"
            onClick={() => setShowModal(true)}
          >
            + New Model
          </button>
        </header>

        <section className="stats">
          <div className="card">
            <span>Total Models</span>
            <h2>{models.length}</h2>
            <p>Available models</p>
          </div>

          <div className="card">
            <span>Trained Models</span>
            <h2>{trainedModels}</h2>
            <p>Successfully trained</p>
          </div>

          <div className="card">
            <span>Fine-tuned</span>
            <h2>{fineTunedModels}</h2>
            <p>Fine-tuned models</p>
          </div>

          <div className="card">
            <span>Experiments</span>
            <h2>{models.length}</h2>
            <p>Total model experiments</p>
          </div>
        </section>

        <section className="content-card">
          <div className="section-header">
            <div>
              <h2>All Models</h2>
              <p>Your AI models</p>
            </div>
          </div>

          <div className="project-list">
            {loading ? (
              <p>Loading models...</p>
            ) : models.length === 0 ? (
              <p>No models found.</p>
            ) : (
              models.map((model) => (
                <div className="project" key={model.id}>
                  <div>
                    <h3>{model.name}</h3>
                    <p>
                      {model.algorithm} • Accuracy: {model.accuracy}
                    </p>
                  </div>

                  <span
                    className={`status ${
                      model.status.toUpperCase() === "TRAINED"
                        ? "active-status"
                        : model.status.toUpperCase() === "COMPLETED"
                        ? "completed"
                        : ""
                    }`}
                  >
                    {model.status}
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

export default Models;