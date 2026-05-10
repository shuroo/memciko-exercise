import { render, screen } from "@testing-library/react";
import App from "./App";

describe("App", () => {

  test("renders task scheduler title", () => {
    render(<App />);

    expect(
      screen.getByText(/Task Scheduler/i)
    ).toBeInTheDocument();
  });

});