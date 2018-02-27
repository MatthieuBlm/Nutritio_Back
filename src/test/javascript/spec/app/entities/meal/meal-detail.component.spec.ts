/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { NutritioBackTestModule } from '../../../test.module';
import { MealDetailComponent } from '../../../../../../main/webapp/app/entities/meal/meal-detail.component';
import { MealService } from '../../../../../../main/webapp/app/entities/meal/meal.service';
import { Meal } from '../../../../../../main/webapp/app/entities/meal/meal.model';

describe('Component Tests', () => {

    describe('Meal Management Detail Component', () => {
        let comp: MealDetailComponent;
        let fixture: ComponentFixture<MealDetailComponent>;
        let service: MealService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [MealDetailComponent],
                providers: [
                    MealService
                ]
            })
            .overrideTemplate(MealDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MealDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MealService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Meal(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.meal).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
