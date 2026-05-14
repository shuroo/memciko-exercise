import { describe, test, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom/vitest";
import App from "../App";

describe("Task Scheduler UI", () => {
  test("renders main title", () => {
    render(<App />);

    expect(
      screen.getByRole("heading", { name: /task scheduler/i })
    ).toBeInTheDocument();
  });

  test("renders create task section title", () => {
    render(<App />);

    expect(
      screen.getByRole("heading", { name: /create task/i })
    ).toBeInTheDocument();
  });

  test("renders create task button", () => {
    render(<App />);

    expect(
      screen.getByRole("button", { name: /create task/i })
    ).toBeInTheDocument();
  });

  test("renders task name input", () => {
    render(<App />);

    expect(
      screen.getByPlaceholderText(/task name/i)
    ).toBeInTheDocument();
  });

  test("renders description input", () => {
    render(<App />);

    expect(
      screen.getByPlaceholderText(/description/i)
    ).toBeInTheDocument();
  });

  test("renders cron expression input", () => {
    render(<App />);

    expect(
      screen.getByPlaceholderText(/cron expression/i)
    ).toBeInTheDocument();
  });

  test("renders log message parameter input", () => {
    render(<App />);

    expect(
      screen.getByPlaceholderText(/log message parameter/i)
    ).toBeInTheDocument();
  });

  test("renders checkboxes", () => {
    render(<App />);

    expect(screen.getByLabelText(/active/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/editable/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/run once/i)).toBeInTheDocument();
  });

  test("renders tasks table", () => {
    render(<App />);

    expect(
      screen.getByRole("heading", { name: /tasks/i })
    ).toBeInTheDocument();

    expect(screen.getByText(/id/i)).toBeInTheDocument();
    expect(screen.getByText(/key/i)).toBeInTheDocument();
    expect(screen.getByText(/name/i)).toBeInTheDocument();
    expect(screen.getByText(/cron/i)).toBeInTheDocument();
    expect(screen.getByText(/actions/i)).toBeInTheDocument();
  });
});