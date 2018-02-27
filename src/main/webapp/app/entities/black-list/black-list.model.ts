import { BaseEntity } from './../../shared';

export class BlackList implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public ingredientEntries?: BaseEntity[],
    ) {
    }
}
