import { useEffect, useState } from "react";
import "./App.css";

const API_URL = "http://localhost:8080/api/tasks";

function App() {
  const [tasks, setTasks] = useState([]);
  const [editingId, setEditingId] = useState(null);

  const [form, setForm] = useState({
    taskKey: "",
    name: "",
    description: "",
    cronExpression: "",
    active: true,
    editable: true,
    runOnce: false,
    startAt: "",
    parameters: {
      message: ""
    }
  });

  useEffect(() => {
    loadTasks();
  }, []);

  async function loadTasks() {
    const response = await fetch(API_URL);
    const data = await response.json();
    setTasks(data);
  }

  function handleChange(event) {
    const { name, value, type, checked } = event.target;

    setForm({
      ...form,
      [name]: type === "checkbox" ? checked : value
    });
  }

  function handleParameterChange(event) {
    setForm({
      ...form,
      parameters: {
        ...form.parameters,
        message: event.target.value
      }
    });
  }

  async function handleSubmit(event) {
    event.preventDefault();

    const payload = {
      ...form,
      taskKey: form.taskKey || null,
      cronExpression: form.runOnce ? null : form.cronExpression,
      startAt: form.runOnce ? form.startAt : null
    };

    const url = editingId ? `${API_URL}/${editingId}` : API_URL;
    const method = editingId ? "PUT" : "POST";

    await fetch(url, {
      method,
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(payload)
    });

    resetForm();
    loadTasks();
  }

  function editTask(task) {
    setEditingId(task.id);

    setForm({
      taskKey: task.taskKey || "",
      name: task.name || "",
      description: task.description || "",
      cronExpression: task.cronExpression || "",
      active: task.active,
      editable: task.editable,
      runOnce: task.runOnce,
      startAt: task.startAt || "",
      parameters: {
        message: task.parameters?.message || ""
      }
    });
  }

  async function deleteTask(id) {
    await fetch(`${API_URL}/${id}`, {
      method: "DELETE"
    });

    loadTasks();
  }

  function resetForm() {
    setEditingId(null);
    setForm({
      taskKey: "",
      name: "",
      description: "",
      cronExpression: "",
      active: true,
      editable: true,
      runOnce: false,
      startAt: "",
      parameters: {
        message: ""
      }
    });
  }

  return (
    <div className="container">
      <h1>Task Scheduler</h1>

      <form onSubmit={handleSubmit} className="form">
        <h2>{editingId ? "Edit Task" : "Create Task"}</h2>

        <input
          name="taskKey"
          placeholder="Task Key (optional)"
          value={form.taskKey}
          onChange={handleChange}
        />

        <input
          name="name"
          placeholder="Task Name"
          value={form.name}
          onChange={handleChange}
          required
        />

        <input
          name="description"
          placeholder="Description"
          value={form.description}
          onChange={handleChange}
        />

        <label>
          <input
            type="checkbox"
            name="active"
            checked={form.active}
            onChange={handleChange}
          />
          Active
        </label>

        <label>
          <input
            type="checkbox"
            name="editable"
            checked={form.editable}
            onChange={handleChange}
          />
          Editable
        </label>

        <label>
          <input
            type="checkbox"
            name="runOnce"
            checked={form.runOnce}
            onChange={handleChange}
          />
          Run Once
        </label>

        {!form.runOnce && (
          <input
            name="cronExpression"
            placeholder="Cron Expression e.g. 0/10 * * * * ?"
            value={form.cronExpression}
            onChange={handleChange}
          />
        )}

        {form.runOnce && (
          <input
            type="datetime-local"
            name="startAt"
            value={form.startAt}
            onChange={handleChange}
          />
        )}

        <input
          placeholder="Log message parameter"
          value={form.parameters.message}
          onChange={handleParameterChange}
        />

        <button type="submit">
          {editingId ? "Update Task" : "Create Task"}
        </button>

        {editingId && (
          <button type="button" onClick={resetForm}>
            Cancel
          </button>
        )}
      </form>

      <h2>Tasks</h2>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Key</th>
            <th>Name</th>
            <th>Cron</th>
            <th>Run Once</th>
            <th>Start At</th>
            <th>Active</th>
            <th>Editable</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {tasks.map((task) => (
            <tr key={task.id}>
              <td>{task.id}</td>
              <td>{task.taskKey}</td>
              <td>{task.name}</td>
              <td>{task.cronExpression}</td>
              <td>{task.runOnce ? "Yes" : "No"}</td>
              <td>{task.startAt}</td>
              <td>{task.active ? "Yes" : "No"}</td>
              <td>{task.editable ? "Yes" : "No"}</td>
              <td>
                <button
                  onClick={() => editTask(task)}
                  disabled={!task.editable}
                >
                  Edit
                </button>

                <button
                  onClick={() => deleteTask(task.id)}
                  disabled={!task.editable}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default App;