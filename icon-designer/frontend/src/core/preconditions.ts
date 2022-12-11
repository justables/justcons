export const Preconditions = {
  notNull<T>(obj: T | null | undefined): T {
    if (obj == null) {
      throw new Error('not null assertion error');
    }
    return obj;
  },
  in<T>(obj: T, list: T[]): T {
    if (!list.includes(obj)) {
      throw new Error('in assertion error');
    }
    return obj;
  },
};
