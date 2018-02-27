/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { NutritioBackTestModule } from '../../../test.module';
import { GoalDetailComponent } from '../../../../../../main/webapp/app/entities/goal/goal-detail.component';
import { GoalService } from '../../../../../../main/webapp/app/entities/goal/goal.service';
import { Goal } from '../../../../../../main/webapp/app/entities/goal/goal.model';

describe('Component Tests', () => {

    describe('Goal Management Detail Component', () => {
        let comp: GoalDetailComponent;
        let fixture: ComponentFixture<GoalDetailComponent>;
        let service: GoalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [GoalDetailComponent],
                providers: [
                    GoalService
                ]
            })
            .overrideTemplate(GoalDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GoalDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GoalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Goal(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.goal).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
