import { BaseEntity } from './../../shared';

export class Grocerie implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public ingredientEntries?: BaseEntity[],
    ) {
    }
}
