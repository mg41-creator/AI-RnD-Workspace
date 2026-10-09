import { useEffect, useState } from "react";

function ResearchNotes({ userId }) {
  const [notes, setNotes] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [projectId, setProjectId] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(true);

  const loadNotes = async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/AI-RnD-Workspace/collaborations"
      );

      const text = await response.text();

      if (text.includes("No collaborations found")) {
        setNotes([]);
        return;
      }

      const blocks = text.split("-------------------------");

      const loadedNotes = blocks
        .map((block) => {
          const id = block.match(/Collaboration ID: (.*)/)?.[1]?.trim();
          const project = block.match(/Project ID: (.*)/)?.[1]?.trim();
          const user = block.match(/User ID: (.*)/)?.[1]?.trim();
          const note = block.match(/Message: (.*)/)?.[1]?.trim();
          const type = block.match(/Type: (.*)/)?.[1]?.trim();
          const date = block.match(/Created At: (.*)/)?.[1]?.trim();

          if (!id || !note || type?.toUpperCase() !== "NOTE") {
            return null;
          }

          return {
            id,
            projectId: project,
            userId: user,
            message: note,
            type,
            date
          };
        })
        .filter(Boolean);

      setNotes(loadedNotes);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadNotes();
  }, []);

  const handleCreateNote = async () => {
    if (!projectId || !message) {
      alert("Please enter project ID and note");
      return;
    }

    try {
      const formData = new URLSearchParams();

      formData.append("projectId", projectId);
      formData.append("userId", userId);
      formData.append("message", message);
      formData.append("type", "NOTE");

      const response = await fetch(
        "http://localhost:8080/AI-RnD-Workspace/collaborations",
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
        alert(result || "Note creation failed.");
        return;
      }

      alert("Research note created successfully!");

      setProjectId("");
      setMessage("");
      setShowModal(false);

      loadNotes();
    } catch (error) {
      alert("Unable to connect to the backend.");
    }
  };

  const thisWeek = notes.length;

  const linkedProjects = new Set(
    notes.map((note) => note.projectId)
  ).size;

  return (
    <>
      {showModal && (
        <div className="modal-overlay">
          <div className="modal-box">
            <h2>Create New Research Note</h2>
            <p>Add a research note to a project.</p>

            <input
              type="number"
              placeholder="Project ID"
              value={projectId}
              onChange={(e) => setProjectId(e.target.value)}
            />

            <textarea
              placeholder="Write your research note..."
              value={message}
              onChange={(e) => setMessage(e.target.value)}
            ></textarea>

            <div className="modal-actions">
              <button onClick={() => setShowModal(false)}>
                Cancel
              </button>

              <button onClick={handleCreateNote}>
                Create Note
              </button>
            </div>
          </div>
        </div>
      )}

      <div className="page">
        <header className="header">
          <div>
            <h1>Research Notes</h1>
            <p>Write and manage your AI research notes.</p>
          </div>

          <button
            className="primary-btn"
            onClick={() => setShowModal(true)}
          >
            + New Note
          </button>
        </header>

        <section className="stats">
          <div className="card">
            <span>Total Notes</span>
            <h2>{notes.length}</h2>
            <p>All research notes</p>
          </div>

          <div className="card">
            <span>This Week</span>
            <h2>{thisWeek}</h2>
            <p>Notes added this week</p>
          </div>

          <div className="card">
            <span>Projects</span>
            <h2>{linkedProjects}</h2>
            <p>Notes linked to projects</p>
          </div>

          <div className="card">
            <span>Drafts</span>
            <h2>0</h2>
            <p>Notes waiting for review</p>
          </div>
        </section>

        <section className="content-card">
          <h2>Recent Research Notes</h2>
          <p>Your latest research notes</p>

          <div className="project-list">
            {loading ? (
              <p>Loading research notes...</p>
            ) : notes.length === 0 ? (
              <p>No research notes found.</p>
            ) : (
              notes.map((note) => (
                <div className="project" key={note.id}>
                  <div>
                    <h3>Project {note.projectId}</h3>
                    <p>{note.message}</p>
                  </div>

                  <span className="status active-status">
                    Note
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

export default ResearchNotes;