import { render, screen } from "@testing-library/react";
import App from "./App";

describe("Task Form", () => {

  test("renders create task button", () => {
    render(<App />);

    expect(
      screen.getByRole("button", {
        name: /Create Task/i
      })
    ).toBeInTheDocument();
  });

  test("renders task name input", () => {
    render(<App />);

    expect(
      screen.getByPlaceholderText(/Task Name/i)
    ).toBeInTheDocument();
  });

});