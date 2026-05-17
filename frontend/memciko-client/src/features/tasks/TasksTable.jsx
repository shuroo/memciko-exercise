import { useDispatch, useSelector } from "react-redux";
import { deleteTask, editTask, fetchTasks } from "./TasksSlice";

function TaskTable() {
  const dispatch = useDispatch();
  const tasks = useSelector((state) => state.tasks.items);

  async function handleDelete(id) {
    await dispatch(deleteTask(id));
    dispatch(fetchTasks());
  }

  return (
    <section className="card">
      <h2>Tasks</h2>

      <div className="table-wrapper">
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
                <td className="table-actions">
                  <button
                    type="button"
                    onClick={() => dispatch(editTask(task))}
                    disabled={!task.editable}
                  >
                    Edit
                  </button>

                  <button
                    type="button"
                    onClick={() => handleDelete(task.id)}
                    disabled={!task.editable}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}

            {tasks.length === 0 && (
              <tr>
                <td colSpan="9" className="empty-state">
                  No tasks yet
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </section>
  );
}

export default TaskTable;