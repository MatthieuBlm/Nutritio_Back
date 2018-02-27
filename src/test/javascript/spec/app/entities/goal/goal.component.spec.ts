/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { NutritioBackTestModule } from '../../../test.module';
import { GoalComponent } from '../../../../../../main/webapp/app/entities/goal/goal.component';
import { GoalService } from '../../../../../../main/webapp/app/entities/goal/goal.service';
import { Goal } from '../../../../../../main/webapp/app/entities/goal/goal.model';

describe('Component Tests', () => {

    describe('Goal Management Component', () => {
        let comp: GoalComponent;
        let fixture: ComponentFixture<GoalComponent>;
        let service: GoalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [GoalComponent],
                providers: [
                    GoalService
                ]
            })
            .overrideTemplate(GoalComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GoalComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GoalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Goal(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.goals[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
