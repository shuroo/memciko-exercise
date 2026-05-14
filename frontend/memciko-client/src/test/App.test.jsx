import { describe, test, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom/vitest";

import App from "../App";

describe("App", () => {
  test("renders task scheduler title", () => {
    render(<App />);

    const title = screen.getByRole("heading", {
      name: /task scheduler/i,
    });

    expect(title).toBeInTheDocument();
  });
});