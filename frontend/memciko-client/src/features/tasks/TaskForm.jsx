import { useDispatch, useSelector } from "react-redux";
import {
  fetchTasks,
  resetForm,
  saveTask,
  setError,
  updateFormField,
  updateMessage
} from "./TasksSlice";

function TaskForm() {
  const dispatch = useDispatch();
  const { form, editingId, error } = useSelector((state) => state.tasks);

  function handleChange(event) {
    const { name, value, type, checked } = event.target;

    dispatch(
      updateFormField({
        name,
        value: type === "checkbox" ? checked : value
      })
    );
  }

  async function handleSubmit(event) {
    event.preventDefault();

    if (!form.runOnce && !form.cronExpression.trim()) {
      dispatch(setError("Cron Expression is required unless Run Once is selected"));
      return;
    }

    await dispatch(saveTask({ form, editingId }));
    dispatch(fetchTasks());
  }

  return (
    <section className="card">
      <h2>{editingId ? "Edit Task" : "Create Task"}</h2>

      {error && <div className="error">{error}</div>}

      <form onSubmit={handleSubmit} className="form">
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

        <div className="checkbox-row">
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
        </div>

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
          onChange={(event) => dispatch(updateMessage(event.target.value))}
        />

        <div className="actions">
          <button type="submit">
            {editingId ? "Update Task" : "Create Task"}
          </button>

          {editingId && (
            <button type="button" onClick={() => dispatch(resetForm())}>
              Cancel
            </button>
          )}
        </div>
      </form>
    </section>
  );
}

export default TaskForm;