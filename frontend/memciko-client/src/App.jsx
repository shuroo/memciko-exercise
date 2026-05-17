import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { fetchTasks } from "./features/tasks/TasksSlice";
import TaskForm from "./features/tasks/TaskForm";
import TaskTable from "./features/tasks/TasksTable";
import "./App.css";

function App() {
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchTasks());
  }, [dispatch]);

  return (
    <main className="app-shell">
      <section className="hero">
        <h1>Task Scheduler</h1>
        <p>Create, edit and manage scheduled tasks</p>
      </section>

      <div className="layout">
        <TaskForm />
        <TaskTable />
      </div>
    </main>
  );
}

export default App;