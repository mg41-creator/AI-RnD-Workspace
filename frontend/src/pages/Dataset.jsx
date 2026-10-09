import { useEffect, useState } from "react";

function Datasets({ userId }) {
  const [datasets, setDatasets] = useState([]);
  const [search, setSearch] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [format, setFormat] = useState("");
  const [sizeMb, setSizeMb] = useState("");
  const [filePath, setFilePath] = useState("");
  const [loading, setLoading] = useState(true);

  const loadDatasets = async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/AI-RnD-Workspace/datasets"
      );

      const text = await response.text();

      if (text.includes("No datasets found")) {
        setDatasets([]);
        return;
      }

      const blocks = text.split("-------------------------");

      const loadedDatasets = blocks
        .map((block) => {
          const id = block.match(/Dataset ID: (.*)/)?.[1]?.trim();
          const datasetName = block.match(/Name: (.*)/)?.[1]?.trim();
          const datasetDescription = block
            .match(/Description: (.*)/)?.[1]
            ?.trim();
          const datasetFormat = block.match(/Format: (.*)/)?.[1]?.trim();
          const size = block.match(/Size: (.*)/)?.[1]?.trim();
          const status = block.match(/Status: (.*)/)?.[1]?.trim();

          if (!id || !datasetName) {
            return null;
          }

          return {
            id,
            name: datasetName,
            description: datasetDescription || "AI Research Dataset",
            format: datasetFormat || "Unknown",
            size: size || "0 MB",
            status: status || "ACTIVE"
          };
        })
        .filter(Boolean);

      setDatasets(loadedDatasets);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadDatasets();
  }, []);

  const handleCreateDataset = async () => {
    if (!name || !format) {
      alert("Please enter dataset name and format");
      return;
    }

    try {
      const formData = new URLSearchParams();

      formData.append("name", name);
      formData.append("description", description);
      formData.append("filePath", filePath);
      formData.append("format", format);
      formData.append("sizeMb", sizeMb || "0");
      formData.append("uploadedBy", userId);

      const response = await fetch(
        "http://localhost:8080/AI-RnD-Workspace/datasets",
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
        alert(result || "Dataset creation failed.");
        return;
      }

      alert("Dataset created successfully!");

      setName("");
      setDescription("");
      setFormat("");
      setSizeMb("");
      setFilePath("");
      setShowModal(false);

      loadDatasets();
    } catch (error) {
      alert("Unable to connect to the backend.");
    }
  };

  const filteredDatasets = datasets.filter((dataset) =>
    dataset.name.toLowerCase().includes(search.toLowerCase())
  );

  const activeDatasets = datasets.filter(
    (dataset) => dataset.status.toUpperCase() === "ACTIVE"
  ).length;

  const processedDatasets = datasets.filter(
    (dataset) =>
      dataset.status.toUpperCase() === "COMPLETED" ||
      dataset.status.toUpperCase() === "READY"
  ).length;

  return (
    <>
      {showModal && (
        <div className="modal-overlay">
          <div className="modal-box">
            <h2>Create New Dataset</h2>
            <p>Add a dataset to your research workspace.</p>

            <input
              type="text"
              placeholder="Dataset name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />

            <input
              type="text"
              placeholder="Format (CSV, JSON, XLSX...)"
              value={format}
              onChange={(e) => setFormat(e.target.value)}
            />

            <input
              type="text"
              placeholder="File path"
              value={filePath}
              onChange={(e) => setFilePath(e.target.value)}
            />

            <input
              type="number"
              placeholder="Size in MB"
              value={sizeMb}
              onChange={(e) => setSizeMb(e.target.value)}
            />

            <textarea
              placeholder="Dataset description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            ></textarea>

            <div className="modal-actions">
              <button onClick={() => setShowModal(false)}>
                Cancel
              </button>

              <button onClick={handleCreateDataset}>
                Create Dataset
              </button>
            </div>
          </div>
        </div>
      )}

      <div className="page">
        <header className="header">
          <div>
            <h1>Research Datasets</h1>
            <p>Manage and explore your AI research datasets.</p>
          </div>

          <button
            className="primary-btn"
            onClick={() => setShowModal(true)}
          >
            + New Dataset
          </button>
        </header>

        <section className="stats">
          <div className="card">
            <span>Total Datasets</span>
            <h2>{datasets.length}</h2>
            <p>Available datasets</p>
          </div>

          <div className="card">
            <span>Active Datasets</span>
            <h2>{activeDatasets}</h2>
            <p>Currently in use</p>
          </div>

          <div className="card">
            <span>Processed</span>
            <h2>{processedDatasets}</h2>
            <p>Successfully processed</p>
          </div>

          <div className="card">
            <span>Total Records</span>
            <h2>{datasets.length}</h2>
            <p>Across all datasets</p>
          </div>
        </section>

        <section className="content-card">
          <div className="section-header">
            <div>
              <h2>All Datasets</h2>
              <p>Browse your research datasets</p>
            </div>

            <input
              type="text"
              placeholder="Search datasets..."
              className="search"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
          </div>

          <div className="project-list">
            {loading ? (
              <p>Loading datasets...</p>
            ) : filteredDatasets.length === 0 ? (
              <p>No datasets found.</p>
            ) : (
              filteredDatasets.map((dataset) => (
                <div className="project" key={dataset.id}>
                  <div>
                    <h3>{dataset.name}</h3>
                    <p>
                      {dataset.format} • {dataset.size}
                    </p>
                  </div>

                  <span
                    className={`status ${
                      dataset.status.toUpperCase() === "ACTIVE"
                        ? "active-status"
                        : dataset.status.toUpperCase() === "COMPLETED"
                        ? "completed"
                        : ""
                    }`}
                  >
                    {dataset.status}
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

export default Datasets;