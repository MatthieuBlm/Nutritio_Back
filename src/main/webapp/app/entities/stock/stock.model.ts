import { BaseEntity } from './../../shared';

export class Stock implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public ingredientEntries?: BaseEntity[],
    ) {
    }
}
