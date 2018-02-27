/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { NutritioBackTestModule } from '../../../test.module';
import { GoalDialogComponent } from '../../../../../../main/webapp/app/entities/goal/goal-dialog.component';
import { GoalService } from '../../../../../../main/webapp/app/entities/goal/goal.service';
import { Goal } from '../../../../../../main/webapp/app/entities/goal/goal.model';
import { PersonService } from '../../../../../../main/webapp/app/entities/person';

describe('Component Tests', () => {

    describe('Goal Management Dialog Component', () => {
        let comp: GoalDialogComponent;
        let fixture: ComponentFixture<GoalDialogComponent>;
        let service: GoalService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [GoalDialogComponent],
                providers: [
                    PersonService,
                    GoalService
                ]
            })
            .overrideTemplate(GoalDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GoalDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GoalService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Goal(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.goal = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'goalListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Goal();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.goal = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'goalListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
