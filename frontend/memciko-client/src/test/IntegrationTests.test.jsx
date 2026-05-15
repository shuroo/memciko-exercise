import { describe, test, expect } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom/vitest";
import App from "../App";

describe("Create Task integration tests", () => {
  test("renders create task form and tasks table", async () => {
    render(<App />);

    expect(
      screen.getByRole("heading", { name: /create task/i })
    ).toBeInTheDocument();

    expect(screen.getByPlaceholderText(/task key/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/task name/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/description/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/cron expression/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/log message parameter/i)).toBeInTheDocument();

    expect(screen.getByRole("checkbox", { name: /active/i })).toBeChecked();
    expect(screen.getByRole("checkbox", { name: /editable/i })).toBeChecked();
    expect(screen.getByRole("checkbox", { name: /run once/i })).not.toBeChecked();

    expect(screen.getByRole("heading", { name: /tasks/i })).toBeInTheDocument();

    expect(await screen.findByText(/LOG_TASK/i)).toBeInTheDocument();
    expect(await screen.findByText(/EMAIL_TASK/i)).toBeInTheDocument();
    expect(await screen.findByText(/DB_QUERY_TASK/i)).toBeInTheDocument();
  });

  test("allows user to fill the form", () => {
    render(<App />);

    fireEvent.change(screen.getByPlaceholderText(/task key/i), {
      target: { value: "TEST_TASK" },
    });

    fireEvent.change(screen.getByPlaceholderText(/task name/i), {
      target: { value: "Test Task" },
    });

    fireEvent.change(screen.getByPlaceholderText(/description/i), {
      target: { value: "Integration test task" },
    });

    fireEvent.change(screen.getByPlaceholderText(/cron expression/i), {
      target: { value: "0/10 * * * * ?" },
    });

    fireEvent.change(screen.getByPlaceholderText(/log message parameter/i), {
      target: { value: "hello test" },
    });

    expect(screen.getByPlaceholderText(/task key/i)).toHaveValue("TEST_TASK");
    expect(screen.getByPlaceholderText(/task name/i)).toHaveValue("Test Task");
    expect(screen.getByPlaceholderText(/description/i)).toHaveValue("Integration test task");
    expect(screen.getByPlaceholderText(/cron expression/i)).toHaveValue("0/10 * * * * ?");
    expect(screen.getByPlaceholderText(/log message parameter/i)).toHaveValue("hello test");
  });

  test("allows user to change checkboxes", () => {
    render(<App />);

    const active = screen.getByRole("checkbox", { name: /active/i });
    const editable = screen.getByRole("checkbox", { name: /editable/i });
    const runOnce = screen.getByRole("checkbox", { name: /run once/i });

    fireEvent.click(active);
    fireEvent.click(editable);
    fireEvent.click(runOnce);

    expect(active).not.toBeChecked();
    expect(editable).not.toBeChecked();
    expect(runOnce).toBeChecked();
  });
});