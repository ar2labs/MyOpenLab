/*
MyOpenLab by Carmelo Salafia www.myopenlab.de
Copyright (C) 2004 Carmelo Salafia cswi@gmx.de

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package ProjectFolder;

public interface ProjectPaletteIF {
  public void projectPaletteDeleteProject(MyNode node);

  public void projectPaletteDeleteVM(MyNode node);

  public void projectPaletteRenameVM(MyNode node);

  public void projectPaletteAddNewProject(MyNode node);

  public void projectPaletteOpenProject(MyNode node);

  public void projectPaletteProjectProperties(MyNode node);

  public void projectPaletteProjectClose(MyNode node);

  public void projectPaletteNewSubVM(MyNode node);

  public void projectPaletteAction(String command, MyNode node);
}
