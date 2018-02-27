import { BaseEntity } from './../../shared';

export const enum Category {
    'FRUIT',
    'VEGETABLE_BEAN',
    'MEAT_FISH',
    'MILK_PRODUCT',
    'DRINK',
    'GRAIN',
    'SWEET',
    'SAUCE_SPICE',
    'OTHER'
}

export class Ingredient implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public brand?: string,
        public energy?: number,
        public protein?: number,
        public carbohydrate?: number,
        public fat?: number,
        public sugar?: number,
        public saturatedFat?: number,
        public fibre?: number,
        public category?: Category,
    ) {
    }
}
