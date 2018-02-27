/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { NutritioBackTestModule } from '../../../test.module';
import { MealComponent } from '../../../../../../main/webapp/app/entities/meal/meal.component';
import { MealService } from '../../../../../../main/webapp/app/entities/meal/meal.service';
import { Meal } from '../../../../../../main/webapp/app/entities/meal/meal.model';

describe('Component Tests', () => {

    describe('Meal Management Component', () => {
        let comp: MealComponent;
        let fixture: ComponentFixture<MealComponent>;
        let service: MealService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [MealComponent],
                providers: [
                    MealService
                ]
            })
            .overrideTemplate(MealComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MealComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MealService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Meal(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.meals[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
