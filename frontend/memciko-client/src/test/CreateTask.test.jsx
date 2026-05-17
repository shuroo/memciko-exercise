import { describe, test, expect, afterEach, beforeEach, vi } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";
import "@testing-library/jest-dom/vitest";

import { Provider } from "react-redux";

import App from "../App";
import store from "../app/store.jsx";

beforeEach(() => {
  globalThis.fetch = vi.fn(() =>
    Promise.resolve({
      ok: true,
      json: () => Promise.resolve([])
    })
  );
});

afterEach(() => {
  cleanup();
  vi.clearAllMocks();
});

function renderApp() {
  render(
    <Provider store={store}>
      <App />
    </Provider>
  );
}

describe("Task Scheduler UI", () => {
  test("renders main title", () => {
    renderApp();

    expect(
      screen.getByRole("heading", {
        level: 1,
        name: /task scheduler/i
      })
    ).toBeInTheDocument();
  });

  test("renders create task section title", () => {
    renderApp();

    expect(
      screen.getByRole("heading", {
        name: /create task/i
      })
    ).toBeInTheDocument();
  });

  test("renders create task button", () => {
    renderApp();

    expect(
      screen.getByRole("button", {
        name: /create task/i
      })
    ).toBeInTheDocument();
  });

  test("renders task name input", () => {
    renderApp();

    expect(
      screen.getByPlaceholderText(/task name/i)
    ).toBeInTheDocument();
  });

  test("renders description input", () => {
    renderApp();

    expect(
      screen.getByPlaceholderText(/description/i)
    ).toBeInTheDocument();
  });

  test("renders cron expression input", () => {
    renderApp();

    expect(
      screen.getByPlaceholderText(/cron expression/i)
    ).toBeInTheDocument();
  });

  test("renders log message parameter input", () => {
    renderApp();

    expect(
      screen.getByPlaceholderText(/log message parameter/i)
    ).toBeInTheDocument();
  });

  test("renders checkboxes", () => {
    renderApp();

    expect(
      screen.getByLabelText(/active/i)
    ).toBeInTheDocument();

    expect(
      screen.getByLabelText(/editable/i)
    ).toBeInTheDocument();

    expect(
      screen.getByLabelText(/run once/i)
    ).toBeInTheDocument();
  });

  test("renders tasks table", () => {
    renderApp();

    expect(
      screen.getByRole("heading", {
        name: /tasks/i
      })
    ).toBeInTheDocument();

    expect(screen.getByText(/id/i)).toBeInTheDocument();
    expect(screen.getByText(/key/i)).toBeInTheDocument();
    expect(screen.getByText(/name/i)).toBeInTheDocument();
    expect(screen.getByText(/cron/i)).toBeInTheDocument();
    expect(screen.getByText(/actions/i)).toBeInTheDocument();
  });
});