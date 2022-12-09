export type InterfaceOf<T> = {
  [P in keyof T]: T[P];
};
